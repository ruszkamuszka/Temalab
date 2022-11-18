package org.example;

import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.common.TypeQLArg;
import com.vaticle.typeql.lang.query.TypeQLDefine;

import static com.vaticle.typeql.lang.TypeQL.type;

public class Schema {

public void writeSchema(TypeDBSession session){
    try{
        // creating a write transaction
        TypeDBTransaction writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
        // write transaction is open
        // write transaction must always be committed (closed)

        TypeQLDefine query = TypeQL.define(
                //attributes
                type("id").sub("attribute").value(TypeQLArg.ValueType.LONG),
                type("Position").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                type("active").sub("attribute").value(TypeQLArg.ValueType.BOOLEAN),
                type("currentPosition").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                type("entry").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                type("exit").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                type("length").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                type("signal").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                type("target").sub("attribute").value(TypeQLArg.ValueType.DOUBLE),
                //name to the reverse edges
                type("name").sub("attribute").value(TypeQLArg.ValueType.STRING),
                //nodes
                type("Route").sub("entity").owns("id", true).owns("active").owns("entry").owns("exit").plays("requires", "Route"),
                type("Segment").sub("entity").owns("id", true).owns("length"),
                type("Semaphore").sub("entity").owns("id", true).owns("signal"),
                type("Region").sub("entity").owns("id", true),
                type("Sensor").sub("entity").owns("id", true).plays("monitoredBy", "Sensor").plays("requires","Sensor"),
                type("Switch").sub("entity").owns("id", true).owns("currentPosition"),
                type("SwitchPosition").sub("entity").owns("id", true).owns("currentPosition").owns("target"),
                type("TrackElement").sub("entity").owns("id", true).plays("connectsTo", "TrackElement1").plays("connectsTo","TrackElement2").plays("monitoredBy","TrackElement1"),

                //edges
                type("connectsTo").sub("relation").owns("id",true).relates("TrackElement1").relates("TrackElement2"),
                type("monitoredBy").sub("relation").owns("id",true).relates("Sensor").relates("TrackElement1"),
                type("requires").sub("relation").owns("id",true).relates("Route").relates("Sensor"),

                //reverse edges
                type("sensor-region").sub("relation").owns("name",true).relates("Sensor").relates("Region"),
                type("semaphore-segment").sub("relation").owns("name",true).relates("Semaphore").relates("Segment"),
                type("switchposotion-route").sub("relation").owns("name",true).relates("SwitchPosition").relates("Route"),
                type("tre-region").sub("relation").owns("name",true).relates("TrackElement").relates("Region")

        );

        writeTransaction.query().define(query);


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
    }
}
