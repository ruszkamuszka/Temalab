package org.example;

import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typeql.lang.TypeQL;
import static com.vaticle.typeql.lang.TypeQL.*;
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
        //client.databases().create("Name");
        TypeDBSession session = client.session("Name", TypeDBSession.Type.DATA);

        try{
            // creating a write transaction
            TypeDBTransaction writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
            // write transaction is open
            // write transaction must always be committed (closed)

            /*TypeQLInsert insertQuery = TypeQL.insert(var("x").isa("person").has("phone-number", "06205169482"));
            List<ConceptMap> insertedId = writeTransaction.query().insert(insertQuery).collect(Collectors.toList());
            writeTransaction.commit();*/

            TypeQLInsert query = TypeQL.match(
                    var("org").isa("company").has("name", "Telecom"),
                    var("person").isa("person").has("phone-number", "06205169482")
            ).insert(
                    var("contract").rel("provider", "org").rel("customer", "person").isa("contract")
            );
            List<ConceptMap> insertedId = writeTransaction.query().insert(query).collect(Collectors.toList());
            writeTransaction.commit();
            // creating a read transaction
            TypeDBTransaction readTransaction = session.transaction(TypeDBTransaction.Type.READ);
            // read transaction is open
            // read transaction must always be closed
            readTransaction.close();

            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // session is open
        session.close();
        // session is closed
        client.close();
        // client is closed
        System.out.println("Hurrá2");

    }
}