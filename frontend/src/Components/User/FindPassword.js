import * as React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Link } from "react-router-dom";
import "./User.css";

export default function FindPassword() {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
    });
  };

  return (
    <Container
      className="login"
      component="main"
      maxWidth="sm"
      style={{ height: "450px" }}
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
          비밀번호 찾기
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <h4>이메일</h4>
          <TextField
            sx={{ backgroundColor: "rgb(255,255,255)"}}
            margin="normal"
            required
            fullWidth
            id="email"
            label="사용중인 이메일 계정을 입력해주세요."
            name="email"
            autoComplete="email"
            autoFocus
          />

          <Button
            sx={{ mt: 3, mb: 3, backgroundColor: "#9DADD8" }}
            type="submit"
            fullWidth
            variant="contained"
          >
            확인
          </Button>
        </Box>
      </Box>
    </Container>
  );
}
