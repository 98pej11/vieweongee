package com.ssafy.vieweongee.controller;

import com.ssafy.vieweongee.service.MeetingService;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sessions")
public class VideoController {
    @Autowired
    MeetingService meetingService;

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
     * 세션 초기화 /api/sessions
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
//        System.out.println("파라미터 정보 >> " + params.toString());
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * 활성화 된 세션 검색 (1개) /api/sessions/{sessionId}
     * @param sessionId 생성된 세션 아이디
     * @return Session Object
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<Session> getSession(@PathVariable("sessionId") String sessionId)
            throws OpenViduJavaClientException, OpenViduHttpException{
        Session session = openvidu.getActiveSession(sessionId);
        if(session == null){ //해당 세션이 없을 때
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    /**
     * 활성화 된 세션 검색 (List) /api/sessions
     * @return Session Object List
     */
    @GetMapping
    public ResponseEntity<List<Session>> getAllActiveSession()
            throws OpenViduJavaClientException, OpenViduHttpException{
        List<Session> sessions = openvidu.getActiveSessions();
        if(sessions == null){ //해당 세션이 없을 때
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    /**
     * 세션의 모든 연결 강제 종료 /api/sessions/{sessionId}
     * @param sessionId 세션 아이디 (세션 아이디를 스터디 아이디로 받아옴)
     * @return 0 : 세션 정상 종료, 1 : 세션이 존재하지않거나 정상 종료되지 않음
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable("sessionId") String sessionId) {

//        System.out.println("studyID >> " + sessionId);

        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색
//        System.out.println("session Id >> "+session.getSessionId());

        if (session == null) {
            return new ResponseEntity<>("session not found", HttpStatus.NOT_FOUND); //세션이 없는 경우
        }

        List<Connection> connections = session.getActiveConnections(); //모든 활성회된 연결 정보를 가져옴
//        System.out.println("Connection size >> "+ connections.size());

        boolean flag = true;
        for(Connection connection : connections){
            try {
//                System.out.println("connection id >> " + connection.getConnectionId());
                session.forceDisconnect(connection); //연결 정상 삭제
            } catch (Exception e) {
                flag = false; //연결 삭제 비정상
                e.printStackTrace();
            }
        }

        if(!flag){
            return new ResponseEntity<>("connection not found", HttpStatus.NOT_FOUND);
        }
        //모든 연결을 삭제했다면 스터디의 진행 상태를 변경해준다
        meetingService.updateStudyProgress(sessionId);

        return new ResponseEntity<>("delete connections complete", HttpStatus.OK);

    }

    /**
     * 세션에서 새 연결 만듦 /api/sessions/{sessionId}/connection
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("/{sessionId}/connection")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색

        //스터디 참가자인지 검증 구현 필요
        //필요 정보 : 스터디 아이디(sessionId), 참가자 아이디
        //로그인한 사용자의 정보는 : 이메일, accesstoken
        //스터디 참가자일때 세션 참가 가능, 참가자기 아니라면 참가 불가능

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //세션이 없는 경우
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    /**
     * 세션에서 연결 반환 (1개) /api/sessions/{sessionId}/connection/{connectionId}
     * @param sessionId 세션 아이디
     * @param connectionId 연결 아이디
     * @return Connection Object
     */
    @GetMapping("/{sessionId}/connection/{connectionId}")
    public ResponseEntity<Connection> getConnection(@PathVariable("sessionId") String sessionId,
                                                    @PathVariable("connectionId") String connectionId)
            throws OpenViduJavaClientException, OpenViduHttpException{
        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색

        openvidu.fetch(); //모든 활성 세션 속성 업데이트

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
     * 세션에서 연결 반환 (List) /api/sessions/{sessionId}/connection
     * @param sessionId 세션 아이디
     * @return Connection Object
     */
    @GetMapping("/{sessionId}/connection")
    public ResponseEntity<List<Connection>> getAllConnection(@PathVariable("sessionId") String sessionId)
            throws OpenViduJavaClientException, OpenViduHttpException{
        Session session = openvidu.getActiveSession(sessionId); //활성화된 해당 세션 검색

        openvidu.fetch(); //모든 활성 세션 속성 업데이트

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //세션이 없는 경우
        }

//        List<Connection> connections = session.getActiveConnections();
        return new ResponseEntity<>(session.getActiveConnections(), HttpStatus.OK);
    }

    // https://docs.openvidu.io/en/stable/reference-docs/REST-API/#delete-connection
    //DELETE /openvidu/api/sessions/<SESSION_ID>/connection/<CONNECTION_ID>
}
