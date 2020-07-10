package local.heftyb.javasprint1.controllers;

import local.heftyb.javasprint1.models.Coin;
import local.heftyb.javasprint1.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{

    @Autowired
    CoinRepository coinrepo;

    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> getTotals()
    {
        double  piggyBankTotal = 0;

        List<Coin> coinsList = new ArrayList<>();
        List<String> totalList = new ArrayList<>();
        coinrepo.findAll().iterator().forEachRemaining(coinsList::add);
        for (Coin c :
            coinsList)
        {
            piggyBankTotal += c.getValue() * c.getQuantity();
            totalList.add(c.getCoinTotal());
        }

        totalList.add("The piggy bank currently holds $" + piggyBankTotal);

        return new ResponseEntity<>(totalList, HttpStatus.OK);
    }
}
