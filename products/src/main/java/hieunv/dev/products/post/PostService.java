package hieunv.dev.products.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostClient postClient;

    @Autowired
    public PostService(PostClient postClient) {
        this.postClient = postClient;
    }

    public List<Post> fetchAllPosts() {
        return postClient.getAllPosts();
    }

    public Post fetchPostById(Long id) {
        return postClient.getPostById(id);
    }
}
