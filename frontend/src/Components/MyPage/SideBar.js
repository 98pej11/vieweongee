import * as React from "react";
import { Link } from "react-router-dom";
import profile from "../../assets/images/mypage-profile.png";
// import icon from "../../assets/icons/mypage_button.png";
import TopicOutlinedIcon from "@mui/icons-material/TopicOutlined";
import Typography from "@mui/material/Typography";
import PropTypes from "prop-types";
import Box from "@mui/material/Box";
import "./MyPage.css";

function Item(props) {
  const { sx, ...other } = props;
  return (
    <Box
      sx={{
        p: 1,
        m: 1,
        bgcolor: () => "rgba(27, 89, 248, 0.1)",
        color: () => "rgba(63, 103, 211, 1)",
        borderRadius: 2,
        fontSize: "1.1rem",
        fontWeight: "600",
        fontFamily: "nexonlv1",
        ...sx,
      }}
      {...other}
    />
  );
}

Item.propTypes = {
  sx: PropTypes.oneOfType([
    PropTypes.arrayOf(
      PropTypes.oneOfType([PropTypes.func, PropTypes.object, PropTypes.bool])
    ),
    PropTypes.func,
    PropTypes.object,
  ]),
};
const SideBar = () => {
  return (
    <div className="SideBar">
      <div>
        <Typography
          fontFamily="tenada"
          fontSize="h4.fontSize"
          fontWeight="bold"
          textAlign="center"
        >
          MyPage
        </Typography>
        <img src={profile} alt="profile" className="mypage-profile" />
        <Typography
          fontSize="lg"
          fontWeight="lg"
          textAlign="center"
          sx={{ mt: 1, mb: 3 }}
        >
          뷰엉님, 반갑습네다
        </Typography>
      </div>
      <Box sx={{ flexDirection: "column", marginTop: 0 }}>
        <Item>Main</Item>
        <Item>Data</Item>
        <Item>Modify Info</Item>
        {/* <Button startIcon={<TopicOutlinedIcon />}>
          <Link to={"/userdelete"}>Delete User</Link>
        </Button> */}
      </Box>
    </div>
  );
};

export default SideBar;
