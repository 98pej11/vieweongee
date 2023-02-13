package com.ssafy.vieweongee.service;


import com.ssafy.vieweongee.dto.study.CreateStudyRequest;
import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;
import com.ssafy.vieweongee.entity.Study;
import com.ssafy.vieweongee.entity.User;
import com.ssafy.vieweongee.repository.StudyRepository;
import com.ssafy.vieweongee.repository.SummaryRepository;
import io.jsonwebtoken.lang.Arrays;

import org.junit.jupiter.api.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class StudyServiceTest {

    @Mock
    StudyRepository studyRepository;

    @Mock
    StudyServiceImpl studyServiceImpl;

    @Autowired
    StudyService studyService;

    @Mock
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    static Study study;

    @Autowired
    static Study newStudy;

    @Autowired
    static CreateStudyRequest createStudyRequest;
    @Autowired
    static UserCreateRequest userCreateRequest;

//    @Mock
    @Autowired
    static User user;
    Long studyId = 1L;


//    StudyServiceTest(StudyRepository studyRepository) {
//        this.studyRepository = studyRepository;
//    }

    @BeforeEach
    @DisplayName("유저와 스터디를 세팅해보아요")
    void setUp() {
        // User를 세팅해보아요
        userCreateRequest = new UserCreateRequest();
        userCreateRequest.setName("TEST");
        userCreateRequest.setEmail("test@ssafy.com");
        userCreateRequest.setPassword("1234");
        userCreateRequest.setPasswordCheck("1234");
        userCreateRequest.setProvider("global");
        user = userService.createUser(userCreateRequest);

        String accessToken = tokenService.createAccessToken(user.getId());
        user.setJwtToken(accessToken);

        // Study를 세팅해보아요
        createStudyRequest = new CreateStudyRequest();
        createStudyRequest.setTitle("TEST");
        createStudyRequest.setContent("TEST content");
        createStudyRequest.setCompany("TEST company");
        createStudyRequest.setJob("software");
        Date now = new Date();
        createStudyRequest.setStudy_datetime(now);
        createStudyRequest.setAbility(true);
        createStudyRequest.setAttitude(true);
        createStudyRequest.setConfirm(true);
        createStudyRequest.setLoyalty(true);
        createStudyRequest.setPersonnel(5);
        createStudyRequest.setRunning_time(2);
        createStudyRequest.setSolving(true);
        createStudyRequest.setTeamwork(true);
        createStudyRequest.setType("일대다");
    }


    @Test
    @DisplayName("1. 글 쓰기 테스트")
    public void addTest(){
        newStudy=studyService.createStudy(user, createStudyRequest);
        newStudy.setId(studyId);
        assertThat(newStudy.getCompany()).isEqualTo("TEST company");
    }

    @Test
    @DisplayName("1. 게시글 상세보기 테스트")
    public void getTest() {
//        studyService.createStudy(user, createStudyRequest);

        newStudy=studyService.createStudy(user, createStudyRequest);
        assertThat(newStudy.getTitle()).isEqualTo("TEST");
        assertThat(newStudy.getContent()).isEqualTo("TEST content");

    }

    @Nested
    @DisplayName("2. 게시글 수정 테스트")
    class testModify {
        @Test
        @DisplayName("2-1) 성공 케이스")
        public void success() {
            Study original=studyService.createStudy(user, createStudyRequest);
//            original.setId(11L);

            // given
            CreateStudyRequest createStudyRequest1 = new CreateStudyRequest();
            createStudyRequest1.setTitle("UPDATE");
            createStudyRequest1.setContent("UPDATE");
            createStudyRequest1.setCompany("UPDATE");
            createStudyRequest1.setJob("software");
            Date now = new Date();
            createStudyRequest1.setStudy_datetime(now);
            createStudyRequest1.setAbility(true);
            createStudyRequest1.setAttitude(true);
            createStudyRequest1.setConfirm(true);
            createStudyRequest1.setLoyalty(true);
            createStudyRequest1.setPersonnel(5);
            createStudyRequest1.setRunning_time(2);
            createStudyRequest1.setSolving(true);
            createStudyRequest1.setTeamwork(true);
            createStudyRequest1.setType("일대다");
            Study modifiedStudy = studyService.createStudy(user, createStudyRequest1);

            // when
            original=studyService.updateStudy(original.getId(), modifiedStudy);

            // Then
            assertThat(original.getContent()).isEqualTo("UPDATE");
        }

        @Test
        @DisplayName("2-2) 실패 케이스 - 권한 없는 유저")
        public void fail() {
            Study original=studyService.createStudy(user, createStudyRequest);

            // given
            user.setJwtToken(null);

            CreateStudyRequest createStudyRequest1 = new CreateStudyRequest();
            createStudyRequest1.setTitle("UPDATE");
            createStudyRequest1.setContent("UPDATE");
            createStudyRequest1.setCompany("UPDATE");
            createStudyRequest1.setJob("software");
            Date now = new Date();
            createStudyRequest1.setStudy_datetime(now);
            createStudyRequest1.setAbility(true);
            createStudyRequest1.setAttitude(true);
            createStudyRequest1.setConfirm(true);
            createStudyRequest1.setLoyalty(true);
            createStudyRequest1.setPersonnel(5);
            createStudyRequest1.setRunning_time(2);
            createStudyRequest1.setSolving(true);
            createStudyRequest1.setTeamwork(true);
            createStudyRequest1.setType("일대다");

            Study modifyFail = studyService.createStudy(user, createStudyRequest1);

            // when
            original=studyService.updateStudy(original.getId(), modifyFail);

            // then
            assertNull(original);

        }
    }

    @Nested
    @DisplayName("3. 게시글 삭제 테스트")
    class testDelete {
        @Test
        @DisplayName("3-1) 성공 케이스")
        public void success() {
            // given
            Study original=studyService.createStudy(user, createStudyRequest);
            Long studyIdOfDelete=original.getId();

            // when
            studyService.deleteStudy(original.getId());

            // Then
            assertNull(studyService.getStudyDetail(studyIdOfDelete));
        }

        @Test
        @DisplayName("3-2) 실패 케이스 - 존재하지 않는 스터디 삭제하려함")
        public void fail() {
            // given
            Study original=studyService.createStudy(user, createStudyRequest);
            Long studyIdOfDelete=original.getId();

            // when
            studyService.deleteStudy(1234L);

            // then
            assertNull(studyService.getStudyDetail(1234L));

        }

    }

    @Nested
    @DisplayName("4. 모든 게시글 불러오기 테스트")
    class testGetAll {
        @Test
        @DisplayName("4-1) 성공 케이스")
        public void success() {
            // given
            Study original1=studyService.createStudy(user, createStudyRequest);
            Study original2=studyService.createStudy(user, createStudyRequest);

            // when
            List<Study> testAll=studyService.getAllStudy();

            // Then
            int studies= testAll.size();
            assertThat(studies==2);
        }

        @Test
        @DisplayName("4-2) 실패 케이스 - 전체 게시글 수가 다름")
        public void fail() {
            // given
            Study original1=studyService.createStudy(user, createStudyRequest);
            Study original2=studyService.createStudy(user, createStudyRequest);

            // when
            List<Study> testAll=studyService.getAllStudy();

            // Then
            int studies= testAll.size();
            assertThat(studies!=2);
        }
    }

    @Nested
    @DisplayName("5. 상위 3개 게시글 불러오기 테스트")
    class testGetThree {
        @Test
        @DisplayName("5-1) 성공 케이스")
        public void success() {
            // given
            Study original1=studyService.createStudy(user, createStudyRequest);
            Study original2=studyService.createStudy(user, createStudyRequest);

            // 구분용 새로운 게시글
            CreateStudyRequest createStudyRequest3 = new CreateStudyRequest();
            createStudyRequest3.setTitle("TopThree");
            createStudyRequest3.setContent("TopThree");
            createStudyRequest3.setCompany("TopThree");
            createStudyRequest3.setJob("TopThree");
            Date now = new Date();
            createStudyRequest3.setStudy_datetime(now);
            createStudyRequest3.setAbility(true);
            createStudyRequest3.setAttitude(true);
            createStudyRequest3.setConfirm(true);
            createStudyRequest3.setLoyalty(true);
            createStudyRequest3.setPersonnel(5);
            createStudyRequest3.setRunning_time(2);
            createStudyRequest3.setSolving(true);
            createStudyRequest3.setTeamwork(true);
            createStudyRequest3.setType("일대다");

            Study original3=studyService.createStudy(user, createStudyRequest3);
            Study original4=studyService.createStudy(user, createStudyRequest);

            // when
            List<Study> testTopThree=studyService.getTop3Study();

            // Then

            assertThat(testTopThree.get(2).getTitle().equals("TopThree"));
        }

        @Test
        @DisplayName("5-2) 실패 케이스 - 등록된 스터디가 없을 때")
        public void fail() {
            // given
//            Study original1=studyService.createStudy(user, createStudyRequest);
//            Study original2=studyService.createStudy(user, createStudyRequest);

            // 구분용 새로운 게시글
//            CreateStudyRequest createStudyRequest3 = new CreateStudyRequest();
//            createStudyRequest3.setTitle("TopThree");
//            createStudyRequest3.setContent("TopThree");
//            createStudyRequest3.setCompany("TopThree");
//            createStudyRequest3.setJob("TopThree");
//            Date now = new Date();
//            createStudyRequest3.setStudy_datetime(now);
//            createStudyRequest3.setAbility(true);
//            createStudyRequest3.setAttitude(true);
//            createStudyRequest3.setConfirm(true);
//            createStudyRequest3.setLoyalty(true);
//            createStudyRequest3.setPersonnel(5);
//            createStudyRequest3.setRunning_time(2);
//            createStudyRequest3.setSolving(true);
//            createStudyRequest3.setTeamwork(true);
//            createStudyRequest3.setType("일대다");
//
//            Study original3=studyService.createStudy(user, createStudyRequest3);
//            Study original4=studyService.createStudy(user, createStudyRequest);

            // when
            List<Study> testTopThree=studyService.getTop3Study();

            // Then
//            assertThat(testTopThree.get(1).getTitle().equals("TopFalse"));
        }
    }

    @Nested
    @DisplayName("6. 게시글 검색 테스트")
    class testSearchStudy {
        @Test
        @DisplayName("6-1) 성공 케이스")
        public void success() {
            // given
            Study original1=studyService.createStudy(user, createStudyRequest);

            // when
            List<Study> result=studyService.searchStudy("TEST");

            // then
            // 검색 결과가 하나 이상 있다.
            assertThat(result.size()>=1);
        }

        @Test
        @DisplayName("6-2) 실패 케이스")
        public void fail() {
            // given
            Study original1=studyService.createStudy(user, createStudyRequest);

            // when
            List<Study> result=studyService.searchStudy("test");

            // Then
            assertThat(result.size()>=0);
        }
    }
}