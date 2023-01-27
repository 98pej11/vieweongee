package com.ssafy.vieweongee.controller;

import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class VideoController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
//        System.out.println(">>>>>>>>>>> init 완료 <<<<<<<<<<<");
    }

    /**
     * 세션 초기화
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("파라미터 정보 >> " + params.toString());
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * 활성화 된 세션 검색 (1개)
     * @param sessionId 생성된 세션 아이디
     * @return Session Object
     */
    @GetMapping("/api/sessions/{sessionId}")
    public ResponseEntity<Session> getSession(@PathVariable("sessionId") String sessionId)
            throws OpenViduJavaClientException, OpenViduHttpException{
        Session session = openvidu.getActiveSession(sessionId);
        if(session == null){ //해당 세션이 없을 때
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    /**
     * 활성화 된 세션 검색 (List)
     * @return Session Object List
     */
    @GetMapping("/api/sessions")
    public ResponseEntity<List<Session>> getAllSession()
            throws OpenViduJavaClientException, OpenViduHttpException{
        List<Session> sessions = openvidu.getActiveSessions();
        if(sessions == null){ //해당 세션이 없을 때
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    /**
     * 세션 종료 >> 안 쓸 것 같음!!
     * @param sessionId 세션 아이디
     * @return 0 : 세션 정상 종료, 1 : 세션이 존재하지않거나 정상 종료되지 않음
     */
//    @DeleteMapping("/api/sessions/{sessionId}")
//    public ResponseEntity<?> deleteSession(@PathVariable("sessionId") String sessionId) {
//        Session session;
//        try{
//            session = openvidu.getActiveSession(sessionId);
//            session.close();
//            return new ResponseEntity<>("delete session complete", HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>("session not found", HttpStatus.NOT_FOUND);
//        }
//    }

    /**
     * 세션에서 새 연결 만듦
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("/api/sessions/{sessionId}/connection")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //세션이 없는 경우
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    /**
     * 세션에서 연결 반환 (1개)
     * @param sessionId 세션 아이디
     * @param connectionId 연결 아이디
     * @return Connection Object
     */
    @GetMapping("/api/sessions/{sessionId}/connection/{connectionId}")
    public ResponseEntity<Connection> getConnection(@PathVariable("sessionId") String sessionId,
                                                    @PathVariable("connectionId") String connectionId)
            throws OpenViduJavaClientException, OpenViduHttpException{
        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //세션이 없는 경우
        }

        Connection connection = session.getConnection(connectionId);
        if(connection == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //연결 못 찾음
        }
        return new ResponseEntity<>(connection, HttpStatus.OK);
    }

    /**
     * 세션에서 연결 반환 (List)
     * @param sessionId 세션 아이디
     * @return Connection Object
     */
    @GetMapping("/api/sessions/{sessionId}/connection")
    public ResponseEntity<List<Connection>> getAllConnection(@PathVariable("sessionId") String sessionId)
            throws OpenViduJavaClientException, OpenViduHttpException{
        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //세션이 없는 경우
        }

        List<Connection> connections = session.getConnections();
        return new ResponseEntity<>(connections, HttpStatus.OK);
    }

    // https://docs.openvidu.io/en/stable/reference-docs/REST-API/#delete-connection
    //DELETE /openvidu/api/sessions/<SESSION_ID>/connection/<CONNECTION_ID>
}
