package com.smuralee.config.model;

import com.smuralee.util.SecretsEnum;
import lombok.Data;

@Data
public class RDSSecret {

    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbname;
    private String dbInstanceIdentifier;

    private String getJdbcPrefix() {
        return SecretsEnum.valueOf(this.getEngine()).getJdbcPrefix();
    }

    public String getJdbcDriverClassName() {
        return SecretsEnum.valueOf(this.getEngine()).getJdbcDriverClassName();
    }

    public String getDatasourceURL() {
        return this.getJdbcPrefix() +
                this.getHost() +
                ":" +
                this.getPort() +
                "/" +
                this.getDBName();
    }

    private String getDBName() {
        String value = null;
        if (this.dbname != null) {
            value = this.dbname;
        } else if (this.dbInstanceIdentifier != null) {
            value = this.dbInstanceIdentifier;
        }
        return value;
    }

}
