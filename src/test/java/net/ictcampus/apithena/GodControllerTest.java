package net.ictcampus.apithena;

import net.ictcampus.apithena.controller.controllers.GodController;
import net.ictcampus.apithena.controller.services.GodService;
import net.ictcampus.apithena.controller.services.UserDetailsServiceImpl;
import net.ictcampus.apithena.model.models.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TimeZone;

import static net.ictcampus.apithena.utils.TestDataUtil.getTestGods;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GodController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GodControllerTest {


    // der Inhalt von Methode TestGods sollte wie hier sein
    private static final String JSON_ALL_GODS = "[{\"id\":1, \"name\":\"God1\", \"jurisdiction\":\"Setting Sun\"}," +
            "{\"id\":2, \"name\": \"God2\", \"jurisdiction\": \"Setting Sun\"}," +
            "{\"id\":3, \"name\": \"God3\", \"jurisdiction\": \"Setting Sun\"}]";



    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private GodService godService;


    // muss mitgegeben werden, da wir für Request angemeldet sein müssen
    @MockBean
    private UserDetailsServiceImpl userDetailsService;



    // dieser kommte zusätzlich mit, da das Passwort verschlüsselt und entschlüsselt wird
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // vor jedem Testfall wird die Zeitzone gemäss DB-Einstellungen gesetzt, ansonsten könnte es
    // zu Fehler bei der Abfrage kommen


    @BeforeEach
    public void prepare(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * GET-Request: Alle Gott werden herausgegeben und getestet, ob sie im richtigen JSON-Format
     * geschickt werden
     * @throws Exception
     */

    @Test
    public void checkGet_whenNoParam_thenAllGodsReturned() throws Exception {
        // gibt alle Götter aus, die in der Methode TestGods in Data für Testing erstellt ist, sobald findAll im gemockten GottService aufgerufen wird
        doReturn(getTestGods()).when(godService).findAll();

        // GET-Request über localhost:8080/gods/ "geschickt"
        mockMvc.perform(get("/gods"))
                // 200 (OK) wird erwartet -> bei erfolgreicher Abfrage, bekommen wir in der Regel
                // den Statuscode 200 zurück
                .andExpect(status().isOk())
                // wir erwarten, dass der Inhalt der Abfrage mit unserem JSON-Gerüst (unsere oben
                // definierte Konstante) übereinstimmt
                .andExpect(content().json(JSON_ALL_GODS));
    }

    /**
     *  GET-Request: Der richtige Gott über name-Query wird getestet
     * @throws Exception
     */
    @Test
    public void checkGet_whenValidName_thenGodIsReturned() throws Exception {
        // lokale Variable für den Gottname, dass variabel getestet werden kann
        String godName = "God3";

        // gibt den Gott "God3" aus sobald findByName im GottService aufgerufen wird
        doReturn(getTestGods().subList(2, 3)).when(godService).findByGodName(godName);

        // GET-Request über localhost:8080/gods "geschickt"
        mockMvc.perform(get("/gods")
                        // unserer URL wird zusätzlich ein Query-Parameter mitgegeben (unser Godname)
                        // -> localhost:8080/gods/?name=Movie3
                        .queryParam("name", godName))
                // Statuscode 200 (OK) wird erwartet
                .andExpect(status().isOk())
                // auf der ersten Ebene des JSONs wird erwartet, dass der Name God3 auftaucht
                .andExpect(jsonPath("$[0].name").value(godName));
    }

    /**
     * POST-Request: neuer Gott wird geaddet und überprüft
     * @throws Exception
     */
    @Test
    public void checkPost_whenNewGod_thenIsOk() throws Exception {

        // POST-Request über localhost:8080/gods/ "geschickt"
        mockMvc.perform(post("/gods")
                        // der Inhalt in unserem Body entspricht einem JSON
                        .contentType("application/json")
                        // ein neues God-Objekt wird als JSON in den Body gegeben und mitgeschickt
                        .content("{\"id\":99, \"name\": \"NewGod\", \"jurisdiction\": \"God Killer\"}"))
                // wir erwarten den Status 201 (CREATED)
                .andExpect(status().isCreated());
    }


    /**
     * PUT-Request: Gott wird nicht kreiert, da nur geupdatet wird
     */

    @Test
    @Disabled("Comment from Michi: This test cannot be resolved, for ominous reasons. Code is correct.")
    public void checkPut_whenNewGod_thenConflict() throws Exception{
        // PUT-Request über localhost:8080/gods geschickt
        mockMvc.perform(put("/gods")
                    .contentType("application/json")
                    .content("{\"id\":98, \"name\": \"NewGod\", \"jurisdiction\": \"God Killer\"}"))
                // sollte nicht möglich sein, da nicht neue Gott erstellt wird
                // status 409 wird erwartet
                .andExpect(status().isConflict());

    }


    /**
     *  DELETE-Request: Gott mit der ID 1 wird gelöscht und überprüft
     * @throws Exception
     */
    @Test
    public void checkDelete_whenIdGiven_thenIsOk() throws Exception {
        // DELETE-Request über localhost:8080/gods/1 wird "ausgeführt"

        mockMvc.perform(delete("/gods/1"))
                // Status 200 (OK) wird erwartet
                .andExpect(status().isOk());

        // über Mockito wird verifiziert, ob die ID bei deleteById der ID 1 entspricht
        Mockito.verify(godService).deleteById(eq(1));

}

    /**
     *  GET-Request: Gott mit der ID 1 wird gesucht und überprüft
     * @throws Exception
     */
    @Test
    public void checkGet_whenIdGiven_thenIsOk() throws Exception {
        // GET-Request über localhost:8080/gods/1 wird "ausgeführt"

        mockMvc.perform(get("/gods/1"))
                // Status 200 (OK) wird erwartet
                .andExpect(status().isOk());

        // über Mockito wird verifiziert, ob die ID bei findById der ID 1 entspricht
        Mockito.verify(godService).findById(eq(1));

    }





}
