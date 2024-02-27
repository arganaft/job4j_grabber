package ru.job4j.grabber;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final Connection connection;

    public PsqlStore(Properties config) {
        try {
            Class.forName(config.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(
            "INSERT INTO post (vacancy_name, link, vacancy_text, created) VALUES (?, ?, ?, ?) ON CONFLICT (link) DO NOTHING")) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getLink());
            preparedStatement.setString(3, post.getDescription());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new LinkedList<>();
        Post post;
        try (PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM post")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                      post = new  Post(
                            resultSet.getString("vacancy_name"),
                            resultSet.getString("link"),
                            resultSet.getString("vacancy_text"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                            );
                      post.setId(resultSet.getInt("id"));
                      posts.add(post);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM post WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    post = new Post(
                            resultSet.getString("vacancy_name"),
                            resultSet.getString("link"),
                            resultSet.getString("vacancy_text"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    );
                    post.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (post == null) {
            throw new NullPointerException(String.format("Post with id %d not found", id));
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}