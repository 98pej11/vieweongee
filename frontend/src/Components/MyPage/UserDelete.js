import * as React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import person from "../../assets/icons/person.png";
import "./MyPage.css";
import { Typography } from "@material-ui/core";

export default function UserDelete() {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

  return (
    <Container component="main">
      <CssBaseline />
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography className="type">
          회원 탈퇴 시, 회원님의 모든 정보가 삭제됩니다.
        </Typography>
        <Typography sx={{ mt: 3, mb: 3, color: "red" }}>
          정말 탈퇴하시겠습니까ㅠ_ㅠ?
        </Typography>

        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="비밀번호 재확인"
            name="email"
            autoComplete="email"
            autoFocus
            sx={{ minWidth: "25rem" }}
          />
          <Button
            sx={{
              mt: 3,
              mb: 3,
              backgroundColor: "#E1E6FF",
              color: "black",
              float: "right",
            }}
            type="submit"
            variant="contained"
          >
            <div className="btn">회원 탈퇴</div>
          </Button>
        </Box>
      </Box>
    </Container>
  );
}
