import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import "./User.css";

const theme = createTheme();

export default function SignUp() {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

  return (
    <Container
      className="signup"
      component="main"
      maxWidth="sm"
      style={{ height: "830px" }}
    >
      <CssBaseline />
      <Box
        sx={{
          margin: "0 auto",
          marginTop: 5,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography
          component="h1"
          variant="h5"
          sx={{ paddingTop: 5, paddingBottom: 5 }}
        >
          회원가입
        </Typography>
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
          <Grid container spacing={2}>
            <h4>이메일</h4>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                required
                fullWidth
                id="email"
                label="이메일 입력"
                name="email"
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={6} md={9}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                required
                fullWidth
                id="email"
                label="이메일 인증번호"
                name="email"
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={6} md={3}>
              <Button
                sx={{
                  mt: 3,
                  mb: 3,
                  backgroundColor: "#9DADD8",
                  margin: 0,
                  height: 55,
                }}
                required
                fullWidth
                type="submit"
                variant="contained"
              >
                확인
              </Button>
            </Grid>

            <h4>비밀번호</h4>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                required
                fullWidth
                name="password"
                label="비밀번호 입력"
                type="password"
                id="password"
                autoComplete="new-password"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                required
                fullWidth
                name="password"
                label="비밀번호 재확인"
                type="password"
                id="password"
                autoComplete="new-password"
              />
            </Grid>
            <h4>닉네임</h4>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                required
                fullWidth
                name="nickname"
                label="닉네임 입력"
                type="nickname"
                id="nickname"
                autoComplete="new-nickname"
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                control={<Checkbox value="allowExtraEmails" color="primary" />}
                label="회원가입에 동의합니다."
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 3, backgroundColor: "#9DADD8", height: 50 }}
          >
            완료
          </Button>
          <Grid container justifyContent="flex-end">
            <Grid item>
              <Link to={"/login"}>이미 회원이십니까 ?</Link>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}
