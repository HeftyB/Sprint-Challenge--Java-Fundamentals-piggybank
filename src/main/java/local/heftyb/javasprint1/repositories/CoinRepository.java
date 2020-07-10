package local.heftyb.javasprint1.repositories;

import local.heftyb.javasprint1.models.Coin;
import org.springframework.data.repository.CrudRepository;

public interface CoinRepository extends CrudRepository<Coin, Long>
{
}
