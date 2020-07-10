package local.heftyb.javasprint1.controllers;

import local.heftyb.javasprint1.models.Coin;
import local.heftyb.javasprint1.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{

    @Autowired
    CoinRepository coinrepo;

    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> getTotals ()
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

    @GetMapping(value = "/money/{amount}", produces = {"application/json"})
    public ResponseEntity<?> removeCoins (@PathVariable
                                              double amount)
    {
        double  piggyBankTotal = 0;
        double amountToRemove = amount;

        List<Coin> coinsList = new ArrayList<>();

        List<Coin> dollarList = new ArrayList<>();
        double dollarListTotal = 0.0;
        int dollarQTY = 0;

        List<Coin> quarterList = new ArrayList<>();
        double quarterListTotal =0.0;
        int quarterQTY = 0;

        List<Coin> dimeList = new ArrayList<>();
        double dimeListTotal = 0.0;
        int dimeQTY = 0;

        List<Coin> nickelList = new ArrayList<>();
        double nickelListTotal = 0.0;
        int nickelQTY = 0;

        List<Coin> pennyList = new ArrayList<>();
        double pennyListTotal = 0.0;
        int pennyQTY = 0;

        coinrepo.findAll().iterator().forEachRemaining(coinsList::add);
        for (Coin c :
            coinsList)
        {
            piggyBankTotal += c.getValue() * c.getQuantity();
            if (c.getValue() == 1.00)
            {
                dollarList.add(c);
                dollarListTotal += c.getValue() * c.getQuantity();
                dollarQTY += c.getQuantity();
            }

            else if (c.getValue() == 0.25)
            {
                quarterList.add(c);
                quarterListTotal += c.getValue() * c.getQuantity();
                quarterQTY += c.getQuantity();
            }

            else if (c.getValue() == 0.10)
            {
                dimeList.add(c);
                dimeListTotal += c.getValue() * c.getQuantity();
                dimeQTY += c.getQuantity();
            }

            else if (c.getValue() == 0.05)
            {
                nickelList.add(c);
                nickelListTotal += c.getValue() * c.getQuantity();
                nickelQTY += c.getQuantity();
            }

            else if (c.getValue() == 0.01)
            {
                pennyList.add(c);
                pennyListTotal += c.getValue() * c.getQuantity();
                pennyQTY += c.getQuantity();
            }
        }

        if (amountToRemove > piggyBankTotal)
        {
            return new ResponseEntity<>("Money is not available", HttpStatus.OK);
        }
        double tempCount = amountToRemove;

        int dollarsToRemove = (int) amountToRemove;
        tempCount = tempCount - dollarsToRemove;
        dollarQTY = dollarQTY - dollarsToRemove;

        int quartersToRemove = (int) (tempCount * 100 )/ 25;
        quarterQTY = quarterQTY - quartersToRemove;
        tempCount = tempCount - (quartersToRemove * 0.25);

        int dimesToRemove = (int) (tempCount * 100) / 10;
        dimeQTY = dimeQTY - dimesToRemove;
        tempCount = tempCount - (dimesToRemove * 0.10);

        int nickelsToRemove = (int) (tempCount * 100) / 05;
        nickelQTY = nickelQTY - nickelsToRemove;
        tempCount = tempCount - (nickelsToRemove * 0.05);

        int penniesToRemove = (int) (tempCount * 100) / 1;
        pennyQTY = pennyQTY - penniesToRemove;
        tempCount = tempCount - (penniesToRemove * 0.01);


        piggyBankTotal = piggyBankTotal - amountToRemove;

        return new ResponseEntity<>("Remaining coins: " + "Dollars: " + dollarQTY + "Quarters: " + quarterQTY + "Dimes: " + dimeQTY + "Nickels: " + nickelQTY + "Pennies: " + pennyQTY + "Totaling: " + piggyBankTotal, HttpStatus.OK);

    }
}
