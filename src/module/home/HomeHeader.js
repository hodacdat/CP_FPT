import React from "react";
import Logo from "../../components/Logo/Logo";
import { NavLink, useNavigate } from "react-router-dom";
import Button from "../../components/button/Button";
import EnsignVN from "../../Images/vn.png";
import EnsignAnh from "../../Images/anh.png";
const HomeNav = [
  {
    id: 1,
    to: "/",
    title: "Home",
  },
  {
    id: 2,
    to: "/About",
    title: "About",
  },
  {
    id: 3,
    to: "/faq",
    title: "FAQ",
  },
  {
    id: 4,
    to: "/newspage",
    title: "News",
  },
  {
    id: 5,
    to: "/book_appointment_guest",
    title: "Book Visist",
  },
];
const HomeHeader = ({ storedName }) => {
  const navigate = useNavigate();
  const handleLogout = () => {
    //log out here
    localStorage.removeItem("token");
    navigate("/login-user");
  };
  return (
    <header className="max-w-[1156px] gap-[46px] mx-auto flex items-center pt-[45px]">
      <div>
        <Logo></Logo>
      </div>
      <div className="flex items-center justify-between w-full">
        <nav className="home-nav">
          <ul>
            {HomeNav.length > 0 &&
              HomeNav.map((item) => {
                return (
                  <li key={item.id}>
                    <NavLink
                      className={({ isActive }) => (isActive ? "active" : null)}
                      to={item.to}
                    >
                      {item.title}
                    </NavLink>
                  </li>
                );
              })}
            <>
              {storedName !== null ? (
                <li>
                  <NavLink to={"/book_appointment"}>Book Appointment</NavLink>
                </li>
              ) : (
                <>
                  <li>
                    <NavLink to={"/book_appointment_guest"}>
                      Book Appointment
                    </NavLink>
                  </li>
                </>
              )}
            </>
          </ul>
        </nav>
        <div className="flex items-center gap-8">
          {storedName !== null ? (
            <Button
              onClick={handleLogout}
              className="!p-[10px_40px] rounded-lg text-[18px]"
            >
              Logout
            </Button>
          ) : (
            <Button
              onClick={() => {
                navigate("/login-user");
              }}
              className="!p-[10px_40px] rounded-lg text-[18px]"
            >
              Login
            </Button>
          )}
          <div className="flex gap-2">
            <div className="w-[50px] h-[35px]">
              <img src={EnsignVN} alt="" />
            </div>
            <div className="w-[50px] h-[35px]">
              <img src={EnsignAnh} alt="" width={50} height={35} />
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default HomeHeader;
