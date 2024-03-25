package hei.school.soratra.endpoint.rest.controller.soratra;

import hei.school.soratra.endpoint.rest.controller.soratra.dto.Soratra;
import hei.school.soratra.service.soratra.SoratraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/soratra")
@RequiredArgsConstructor
public class SoratraController {

    private final SoratraService soratraService;

    @PutMapping("/{id}")
    public void putSoratra(
            @PathVariable String id,
            @RequestBody byte[] soratra
    ) throws IOException {
        soratraService.saveSoratra(id, soratra);
    }

    @GetMapping("/{id}")
    public Soratra getSoratra(@PathVariable String id) {
        return soratraService.getSoratra(id).orElse(null);
    }

}