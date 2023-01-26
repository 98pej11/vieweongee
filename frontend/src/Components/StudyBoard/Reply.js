import * as React from "react";
import Box from "@mui/joy/Box";
import PermIdentityIcon from "@mui/icons-material/PermIdentity";
import Typography from "@mui/joy/Typography";
import "./StudyBoard.css";
import TextField from "@mui/material/TextField";
import AccountCircle from "@mui/icons-material/AccountCircle";

const Reply = () => {
  return (
    <div className="readstudydetail" variant="outlined" sx={{ width: 500 }}>
      <Typography
        fontFamily="nexonbold"
        fontSize="lg"
        fontWeight="lg"
        sx={{ mt: 1, mb: 2 }}
      >
        댓글
      </Typography>
      {/* <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
        <AccountCircle sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
        <TextField id="input-with-sx" label="댓글을 입력하세요." variant="standard" />
      </Box> */}

      <TextField
        sx={{ backgroundColor: "rgb(255,255,255)" }}
        required
        fullWidth
        id="email"
        label="이메일 인증번호"
        name="email"
        autoComplete="email"
      />

      <Typography
        fontFamily="nexonbold"
        fontSize="lg"
        fontWeight="lg"
        sx={{ mt: 5, mb: 2 }}
      >
        바근정 | 2023.01.29
      </Typography>

      <Box
        className="card-contents"
        sx={{ display: "flex"}}
      >
        <Typography className="content-type" fontSize="lg">
          비전공자는 왜 안되나욤 ?
        </Typography>
      </Box>
    </div>
  );
};

export default Reply;
