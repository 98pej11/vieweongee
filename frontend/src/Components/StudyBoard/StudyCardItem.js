import * as React from "react";
import Box from "@mui/joy/Box";
import PermIdentityIcon from "@mui/icons-material/PermIdentity";
import Typography from "@mui/joy/Typography";
import "./StudyBoard.css";

const StudyCardItem = ({ title, person, ent, dept, date, id }) => {
  return (
    <div className="studycard-div" variant="outlined" sx={{ width: 320 }}>
      <Box className="type">
        <PermIdentityIcon />
        <Typography level="h2" fontSize="md">
          {person}
        </Typography>
      </Box>
      <Typography
        fontFamily="nexonbold"
        fontSize="lg"
        fontWeight="lg"
        textAlign="center"
        sx={{ mt: 1, mb: 3 }}
      >
        {title}
      </Typography>
      <Box
        className="studycard-contents"
        sx={{ display: "flex", justifyContent: "space-between" }}
      >
        <Typography className="content-type" fontSize="lg">
          기업
        </Typography>
        <Typography fontSize="lg" sx={{ display: "justify" }}>
          {ent}
        </Typography>
      </Box>
      <Box className="studycard-contents">
        <Typography className="content-type" fontSize="lg">
          직무
        </Typography>
        <Typography fontSize="lg">{dept}</Typography>
      </Box>
      <Box className="studycard-contents">
        <Typography className="content-type" fontSize="lg">
          날짜
        </Typography>
        <Typography fontSize="lg">{date}</Typography>
      </Box>
    </div>
  );
};

export default StudyCardItem;
