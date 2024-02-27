package ru.job4j.grabber;

import java.util.LinkedList;
import java.util.List;

public class MemStore implements  Store {
    private int id = 0;
    private List<Post> list = new LinkedList<>();

    @Override
    public void save(Post post) {
        post.setId(id);
        list.add(post);
        id++;
    }

    @Override
    public List<Post> getAll() {
        return list;
    }

    @Override
    public Post findById(int id) {
        Post findPost = null;
        for (Post post : list) {
            if (post.getId() == id) {
                findPost = post;
                break;
            }
        }
        if (findPost == null) {
            throw new IllegalArgumentException(String.format("Post with id %d not found", id));
        }
        return findPost;
    }
}
