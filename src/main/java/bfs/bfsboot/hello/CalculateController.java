package bfs.bfsboot.hello;

import bfs.bfsboot.bfs.Surface;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculateController {
    @RequestMapping("/")
    public String index() {
//        Surface surface = new Surface(5, 5);
//        return surface + "\n\n" + surface.calculateVolume();
        return "Welcome to the best water calculator in the world!\n" +
                "Use \"volumes/x/y\" to get the result.";
    }

    @GetMapping("/volumes/{x}/{y}")
    public String calculateVolume(@PathVariable int x, @PathVariable int y) {
        Surface surface = new Surface(x, y);
        return surface + "\n\n" + surface.calculateVolume();
    }
}
