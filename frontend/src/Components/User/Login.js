import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import "./Login.css";

export default function Login() {
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
      className="login"
      component="main"
      maxWidth="sm"
      style={{ height: "650px" }}
    >
      <CssBaseline />
      <Box
        sx={{
          marginTop: 25,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "secondary.main", marginTop: 5 }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          로그인
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <h4>이메일</h4>
          <TextField
            sx={{ backgroundColor: "rgb(255,255,255)" }}
            margin="normal"
            required
            fullWidth
            id="email"
            label="이메일 입력"
            name="email"
            autoComplete="email"
            autoFocus
          />
          <h4>비밀번호</h4>
          <TextField
            sx={{ backgroundColor: "rgb(255,255,255)" }}
            margin="normal"
            required
            fullWidth
            name="password"
            label="비밀번호 입력"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <FormControlLabel
            sx={{ mt: 3 }}
            control={<Checkbox value="remember" color="primary" />}
            label="아이디 저장"
          />
          <Button
            sx={{ mt: 3, mb: 3, backgroundColor: "#9DADD8" }}
            type="submit"
            fullWidth
            variant="contained"
          >
            로그인
          </Button>
          <Grid container>
            <Grid item xs>
              <Link href="#" variant="body2">
                비밀번호 찾기
              </Link>
            </Grid>
            <Grid item>
              <Link href="./Components/SignUp.js" variant="body2">
                {"계정이 없으신가요?"}
              </Link>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}
