package bfs.bfsboot.bfs;

import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class CalculateController {
    @RequestMapping("/")
    public String index() {
        return "Welcome to the best water calculator in the world!\n" +
                "Use \"volumes/x/y\" to get the result.";
    }

    @GetMapping("/volumes/{x}/{y}")
    public String calculateVolume(@PathVariable int x, @PathVariable int y) {
        Surface surface = new Surface(x, y);
        Date start = new Date();
        int v = surface.calculateVolume();
        Date finish = new Date();
        return surface.toWebString() + "<br><br>" + "Volume: " + v + "<br>" + "Time: " + (finish.getTime() - start.getTime()) + " ms";
    }
}
