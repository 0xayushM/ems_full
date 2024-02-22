import React, { useEffect, useState } from "react";
import { createEmployee, getEmployee, updateEmployee} from "../services/EmployeeService";
import { useNavigate, useParams } from "react-router-dom";

export const Create = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const {id} = useParams()

  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
  });

  

  function validateForm() {
    let valid = true;

    const errorsCopy = { ...errors };

    if (firstName.trim()) {
      errorsCopy.firstName = "";
    } else {
      errorsCopy.firstName = "First name is required";
      valid = false;
    }
    if (lastName.trim()) {
      errorsCopy.lastName = "";
    } else {
      errorsCopy.lastName = "Last name is required";
      valid = false;
    }
    if (email.trim()) {
      errorsCopy.email = "";
    } else {
      errorsCopy.email = "Email is required";
      valid = false;
    }
    setErrors(errorsCopy);
    console.log(valid)
    return valid;
  }

  function pageTitle() {
    if(id) {
      return <h2 className="text-center">Update Employee Details</h2>
    } else {
      return <h2 className="text-center">Add New Employee</h2>
    }
  }

  const navigator = useNavigate();

  function saveOrUpdateEmployee(e) {
    e.preventDefault();
    if (validateForm()) {
      const employee = { firstName, lastName, email };
      console.log(employee);
  
      if (id) {
        updateEmployee(id, employee)
          .then((response) => {
            console.log(response.data);
            navigator('/employees');
          })
          .catch((error) => {
            console.error(error);
            alert(error.response.data)
          });
      } else {
        createEmployee(employee)
          .then((response) => {
            console.log(response.data);
            navigator('/employees');
          })
          .catch((error) => {
            console.error(error);
              alert(error.response.data)
          });
      }
    }
  }

  useEffect(() => {
    if(id) {
      getEmployee(id).then((response) => {
        setFirstName(response.data.firstName)
        setLastName(response.data.lastName)
        setEmail(response.data.email)
      }).catch(error => {
        console.log(error)
      })
    }
  },[])

  return (
    <div className="container">
      <br></br>
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          {pageTitle()}
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">First Name: </label>
                <input
                  type="text"
                  placeholder="Enter employee first name"
                  name="firstName"
                  value={firstName}
                  className= {`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                  onChange={(e) => setFirstName(e.target.value)}
                ></input>
                {errors.firstName && <div className="invalid-feedback">{errors.firstName}</div>}
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Last Name: </label>
                <input
                  type="text"
                  placeholder="Enter employee last name"
                  name="lastName"
                  value={lastName}
                  className= {`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                  onChange={(e) => setLastName(e.target.value)}
                ></input>
                {errors.lastName && <div className="invalid-feedback">{errors.lastName}</div>}
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Email: </label>
                <input
                  type="email"
                  placeholder="Enter employee email"
                  name="email"
                  value={email}
                  className={`form-control ${errors.email ? 'is-invalid' : ""}`}
                  onChange={(e) => setEmail(e.target.value)}
                ></input>
                {errors.email && <div className="invalid-feedback">{errors.email}</div>}
              </div>
              <button className="btn btn-success" onClick={saveOrUpdateEmployee}>
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
