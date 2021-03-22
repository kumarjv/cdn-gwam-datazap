package com.manulife.it.datazap.repository.mps.storedproc;

import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("!test")
@Slf4j
public class CloneProfileStoredProcedureImpl  extends StoredProcedure{
	
	private static final String STORED_PROC_NAME = "dspCloneProfile";

	  private static final String TASK_LOG = "sTaskLog";

	  private static final String CLONE_PROFILE = "iProfileToClone";

	  private static final String ROLE_TYPE = "iRoleType";

	  private static final String NAL_PRODUCTID = "iNalProduct";

	  private static final String ADD_FUNCTION = "FunctionsToAdd";

	  private static final String REMOVE_FUNCTION = "FunctionsToRemove";

	  /**
	   * Initialization with datasource
	   *
	   * @param dataSource
	   *          DataSource
	   */
	  @Autowired
	  public CloneProfileStoredProcedureImpl(@Qualifier("mpsDataSource") final DataSource dataSource) {
	    super(dataSource, STORED_PROC_NAME);
	  }

	  /**
	   * declareParameter
	   *
	   */
	  @PostConstruct
	  private void declareParameter() {
	    // in params
	    super.declareParameter(new SqlParameter(TASK_LOG, Types.VARCHAR));
	    super.declareParameter(new SqlParameter(CLONE_PROFILE, Types.INTEGER));
	    super.declareParameter(new SqlParameter(ROLE_TYPE, Types.INTEGER));
	    super.declareParameter(new SqlParameter(NAL_PRODUCTID, Types.INTEGER));
	    super.declareParameter(new SqlParameter(ADD_FUNCTION, Types.INTEGER));
	    super.declareParameter(new SqlParameter(REMOVE_FUNCTION, Types.INTEGER));
	    compile();
	  }

	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  public List<Object> getInfo() {
	    log.info("Populating stored proc input params");
	    final Map<String, Object> storedProcInput = new HashMap<>(6);
	    storedProcInput.put(TASK_LOG, "PIPEDA");
	    storedProcInput.put(CLONE_PROFILE, 2);
	    storedProcInput.put(ROLE_TYPE, 25);
	    storedProcInput.put(NAL_PRODUCTID, 10037);
	    storedProcInput.put(ADD_FUNCTION, 2);
	    storedProcInput.put(REMOVE_FUNCTION, 2);

	    // Call stored procedure
	    log.info("Calling stored proc {}", STORED_PROC_NAME);

	    final Map<String, Object> result = this.execute(storedProcInput);

	    final List<Object> list = new ArrayList<>();

	    log.info("Verifying if data is present in result with keys {}", result.keySet());
	    if (Objects.nonNull(result.get("#result-set-1"))) {
	      final Gson gson = new Gson();
	      final List resultSet = (List) result.get("#result-set-1");

	      resultSet.forEach((final Object resultObj) -> {
	        final Map resultMap = (Map) resultObj;
	        list.add(gson.fromJson(gson.toJsonTree(resultMap), Object.class));
	      });

	      log.info("List of data retrived with size {}", list.size());
	      return list;
	    }

	    log.info("No sponsor payroll data found");
	    return list;
	  }
	
	

}
