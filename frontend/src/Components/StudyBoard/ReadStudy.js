import * as React from "react";
import Box from "@mui/joy/Box";
import PermIdentityIcon from "@mui/icons-material/PermIdentity";
import Typography from "@mui/joy/Typography";
import "./ReadStudy.css";

const ReadStudy = () => {
  return (
    <div className="readstudy" variant="outlined" sx={{ width: 500 }}>
      <h4>싸피 비전공자 면접스터디 구해요!</h4>
      <Typography
        fontFamily="nexonbold"
        fontSize="lg"
        fontWeight="lg"
        textAlign="center"
        sx={{ mt: 1, mb: 3 }}
      >
        작성자 | 날짜
      </Typography>

      <Box
        className="card-contents"
        sx={{ display: "flex", justifyContent: "space-between" }}
      >
        <Typography className="content-type" fontSize="lg">
          기업
        </Typography>
        <Typography fontSize="lg" sx={{ display: "justify" }}>
          신한은행
        </Typography>
      </Box>

      <Box className="card-contents">
        <Typography className="content-type" fontSize="lg">
          직군
        </Typography>
        <Typography fontSize="lg">프론트엔드</Typography>
      </Box>

      <Box className="card-contents">
        <Typography className="content-type" fontSize="lg">
          날짜
        </Typography>
        <Typography fontSize="lg">2023.01.20 18:00 PM</Typography>
      </Box>

      <Box className="card-contents">
        <Typography className="content-type" fontSize="lg">
          유형
        </Typography>
        <Typography fontSize="lg">다대다</Typography>
      </Box>
    
      <h6>소개</h6>
      <p>싸피 10기 비전공자 면접 스터디를 모집합니다.</p>
    </div>
  );
};

export default ReadStudy;
