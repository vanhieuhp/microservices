package hieunv.dev.openfeign.post;

import hieunv.dev.openfeign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "jsonplaceholder",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = FeignConfiguration.class)
public interface PostClient {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getAllPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);
}
