package com.meteo.meteo.interfaces;

import com.meteo.meteo.entities.ComingData;
import com.meteo.meteo.entities.CompiledData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ComingDataInterface extends CrudRepository<ComingData,Integer> {

   List<ComingData> getAllByTokenId(long tokenId);
   @Query("select comingData from ComingData ")
   List<String> getAllComingData();
   void saveJSONintoTable(String json);

}
