import logo_mascot from "../../assets/icons/logo_mascot.png";
import logo_text from "../../assets/icons/logo_vieweongee.png";

import React from "react";

const MainCenter = () => {
  return (
    <div className="MainCenter">
      <div>
        <img src={logo_mascot} className="logo-mascot" alt="logo" />
      </div>
      <div>
        <img src={logo_text} className="logo-text" alt="logo" />
      </div>
    </div>
  );
};

export default MainCenter;
