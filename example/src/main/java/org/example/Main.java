package org.example;

import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typeql.lang.TypeQL;
import static com.vaticle.typeql.lang.TypeQL.*;

import com.vaticle.typeql.lang.common.TypeQLArg;
import com.vaticle.typeql.lang.query.TypeQLDefine;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import com.vaticle.typeql.lang.query.TypeQLInsert;
import com.vaticle.typedb.client.api.answer.ConceptMap;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

class TypeDBQuickstartA {
    public static void main(String[] args) {
        System.out.println("Hurrá");
        TypeDBClient client = TypeDB.coreClient("localhost:1729");
        // client is open
        client.databases().create("TRNEW1");
        TypeDBSession session = client.session("TRNEW1", TypeDBSession.Type.SCHEMA);
        Schema schema = new Schema();
        schema.writeSchema(session);

        // session is closed
        client.close();
        // client is closed
        System.out.println("Hurrá2");

    }
}