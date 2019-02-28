package com.lambdaschool.gdp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class GdpController {

    private final GdpDataRepository repo;
    private final RabbitTemplate rt;

    public GdpController(GdpDataRepository repo, RabbitTemplate rt) {
        this.repo = repo;
        this.rt = rt;
    }

    @GetMapping("/names")
    public ArrayList<GdpData> all() {
        ArrayList<GdpData> data = new ArrayList<>();
        data.addAll(repo.findAll());
        data.sort((g1, g2) -> g1.getCountry().compareToIgnoreCase(g2.getCountry()));
        return data;
    }

    @GetMapping("/economy")
    public ArrayList<GdpData> mostToLeastGDP() {
        ArrayList<GdpData> data = new ArrayList<>();
        data.addAll(repo.findAll());
        findCountries(data, g1 -> (g1.getGdp() >= 0));
        return data;
    }

    @GetMapping("/total")
    public ObjectNode getTotal() {
        List<GdpData> data = repo.findAll();
        Long total = 0L;
        for (GdpData g: data) {
            total = total + g.getGdp();
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode totalGdp = mapper.createObjectNode();
        totalGdp.put("id", 0);
        totalGdp.put("country", "total");
        totalGdp.put("gdp", total);
        return totalGdp;
    }

    @GetMapping("/gdp/{country}")
    public GdpData getCountry(@PathVariable String country) {
        GdpLog log = new GdpLog("Checked country: " + country);
        rt.convertAndSend(GdpApplication.QUEUE, log.toString());

        List<GdpData> data = repo.findAll();
        for (GdpData g: data) {
            if (g.getCountry().toLowerCase().replaceAll("\\s", "").equals(country)){
                return g;
            }
        }return null;

    }

    @PostMapping("/gdp")
    public List<GdpData> newData(@RequestBody List<GdpData> newData) {
        return repo.saveAll(newData);
    }

    public ArrayList<GdpData> findCountries(ArrayList<GdpData> list, CheckGdp tester) {
        ArrayList<GdpData> foundCountries = new ArrayList<GdpData>();
        for (GdpData c : list) {
            if (tester.test(c)) {
                foundCountries.add(c);
            }
        }
        return foundCountries;
    }
}
