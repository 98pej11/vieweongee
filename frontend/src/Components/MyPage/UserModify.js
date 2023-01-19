import * as React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import pwd from "../../assets/icons/pwd.png";
import person from "../../assets/icons/person.png";

export default function UserModify() {
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
      style={{ height: "30%" }}
    >
      <CssBaseline />
      <Box
        sx={{
          marginTop: 10,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Box component="form" noValidate onSubmit={handleSubmit}>
          <Grid container>
            <div className="type">
              <img src={pwd} alt="logo" className="icon" />
              <p>비밀번호 변경</p>
            </div>
            <Grid item xs={12}>
              <TextField
                className="hi"
                sx={{ backgroundColor: "rgb(255,255,255)", mb: 2 }}
                required
                fullWidth
                name="password"
                label="변경할 비밀번호 입력"
                type="password"
                id="password"
                autoComplete="new-password"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)", mb: 5 }}
                required
                fullWidth
                name="password"
                label="비밀번호 재확인"
                type="password"
                id="password"
                autoComplete="new-password"
              />
            </Grid>
            <div className="type">
              <img src={person} alt="logo" className="icon" />
              <p>닉네임 변경</p>
            </div>
            <Grid item xs={12}>
              <TextField
                sx={{ backgroundColor: "rgb(255,255,255)" }}
                required
                fullWidth
                name="nickname"
                label="변경할 닉네임 입력"
                type="nickname"
                id="nickname"
                autoComplete="new-nickname"
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            variant="contained"
            sx={{
              mt: 3,
              mb: 3,
              backgroundColor: "#9DADD8",
              height: 50,
              float: "right",
            }}
          >
            수정완료
          </Button>
        </Box>
      </Box>
    </Container>
  );
}
