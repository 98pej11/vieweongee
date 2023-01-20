import * as React from "react";
import Box from "@mui/joy/Box";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import "./StudyBoard.css";
import { Textarea } from "@mui/joy";

const CreateStudy = () => {
  // 여기 데이터 부분 수정
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

  return (
    <div className="createstudy" variant="outlined" sx={{ width: 500 }}>
      <Box
        sx={{
          margin: "0 auto",
          padding: 5,
          alignItems: "center",
        }}
      >
        <h2>스터디 만들기</h2>
        <Box className="createform" component="form" onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item>
              <h4>제목</h4>
            </Grid>
            <Grid item sm={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                margin="normal"
                required
                fullWidth
                id="subject"
                label="제목 입력"
                name="subject"
                autoComplete="subject"
                autoFocus
              />
            </Grid>

            <Grid item xs={6} md={6}>
              <h4>기업</h4>
            </Grid>
            <Grid item xs={6} md={6}>
              <h4>직군</h4>
            </Grid>

            <Grid item xs={6} md={6}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                margin="normal"
                required
                fullWidth
                name="password"
                label="기업 입력"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>

            <Grid item xs={6} md={6}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                margin="normal"
                required
                fullWidth
                name="password"
                label="직군 입력"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>

            <Grid item>
              <h4>날찌</h4>
            </Grid>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                margin="normal"
                required
                fullWidth
                name="password"
                label="날찌"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>

            <Grid item xs={6} md={6}>
              <h4>인원</h4>
            </Grid>
            <Grid item xs={6} md={6}>
              <h4>인원 유형</h4>
            </Grid>

            <Grid item xs={6} md={6}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                margin="normal"
                required
                fullWidth
                name="password"
                label="인원"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>

            <Grid item xs={6} md={6}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                margin="normal"
                required
                fullWidth
                name="password"
                label="인원 유형"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>

            <Grid item xs={12}>
              <div className="type">
                <Button
                  type="submit"
                  variant="contained"
                  alignItems="center"
                  fullWidth
                  sx={{
                    mt: 3,
                    mb: 3,
                    backgroundColor: "#9DADD8",
                    height: 50,
                  }}
                >
                  채점 템플릿 선택
                </Button>
              </div>
            </Grid>

            <Grid item>
              <h4>소개</h4>
            </Grid>

            <Grid item xs={12}>
              <Textarea
                sx={{
                  height: 200,
                  borderColor: "rgba(77, 77, 78, 0.3)",
                  boxShadow: "1px 10px 15px 1px rgb(221, 221, 221)",
                }}
              ></Textarea>
            </Grid>

            <Grid item xs={12}>
              <div className="type">
                <Button
                  type="submit"
                  variant="contained"
                  alignItems="center"
                  // fullWidth
                  sx={{
                    mt: 3,
                    mb: 3,
                    backgroundColor: "#9DADD8",
                    height: 50,
                    width: 150,
                  }}
                >
                  완료
                </Button>
              </div>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </div>
  );
};

export default CreateStudy;
