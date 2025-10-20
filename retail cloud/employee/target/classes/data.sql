-- Seed data for trial run: 3 departments and 25 employees

-- Departments (head_employee_id left NULL for now)
INSERT INTO department (id, name, creation_date, head_employee_id) VALUES (1, 'Engineering', '2020-01-01', NULL);
INSERT INTO department (id, name, creation_date, head_employee_id) VALUES (2, 'Sales', '2020-02-01', NULL);
INSERT INTO department (id, name, creation_date, head_employee_id) VALUES (3, 'HR', '2020-03-01', NULL);

-- Employees (explicit ids to make manager relationships simple)
INSERT INTO employee (id, name, dob, salary, address, role_title, joining_date, yearly_bonus_percentage, manager_id, department_id) VALUES
(1, 'John CEO', '1975-05-20', 150000, '1 Main St', 'CEO', '2015-01-01', 20, NULL, 1),
(2, 'Emma EngHead', '1980-04-11', 130000, '2 Oak Ave', 'Head of Engineering', '2016-03-15', 15, 1, 1),
(3, 'Liam EngSr', '1985-07-23', 100000, '3 Pine Rd', 'Senior Engineer', '2017-06-01', 10, 2, 1),
(4, 'Olivia EngSr', '1990-02-10', 98000, '4 Cedar Ln', 'Senior Engineer', '2018-09-01', 10, 2, 1),
(5, 'Noah Eng', '1992-11-05', 80000, '5 Birch Pl', 'Engineer', '2019-05-20', 8, 3, 1),
(6, 'Ava Eng', '1993-08-30', 78000, '6 Spruce Dr', 'Engineer', '2019-08-12', 8, 3, 1),
(7, 'Sophia Eng', '1994-12-12', 76000, '7 Willow St', 'Engineer', '2020-01-10', 7, 4, 1),
(8, 'Isabella Eng', '1991-03-03', 77000, '8 Maple Ave', 'Engineer', '2019-11-01', 7, 4, 1),
(9, 'Mia SalesHead', '1982-06-18', 120000, '9 Market Rd', 'Head of Sales', '2016-07-01', 12, 1, 2),
(10, 'Mason Sales', '1987-09-21', 85000, '10 Commerce Blvd', 'Sales Manager', '2018-02-15', 8, 9, 2),
(11, 'Lucas Sales', '1990-10-10', 70000, '11 Trade St', 'Sales Executive', '2019-03-01', 6, 9, 2),
(12, 'Ethan Sales', '1991-01-30', 68000, '12 Retail Ave', 'Sales Executive', '2019-06-01', 6, 9, 2),
(13, 'Amelia Sales', '1992-04-04', 66000, '13 Outlet Rd', 'Sales Executive', '2020-02-10', 5, 10, 2),
(14, 'Harper Sales', '1993-05-15', 65000, '14 Exchange Ln', 'Sales Executive', '2020-04-20', 5, 10, 2),
(15, 'Evelyn HRHead', '1981-12-01', 110000, '15 People Ct', 'Head of HR', '2016-08-01', 12, 1, 3),
(16, 'Abigail HR', '1988-07-07', 70000, '16 Benefit St', 'HR Manager', '2018-05-10', 7, 15, 3),
(17, 'Ella HR', '1990-02-28', 65000, '17 Onboard Ave', 'HR Executive', '2019-07-01', 6, 15, 3),
(18, 'Chloe HR', '1992-09-09', 64000, '18 Culture Blvd', 'HR Executive', '2020-03-03', 6, 15, 3),
(19, 'Grace HR', '1993-11-11', 63000, '19 Recruit Rd', 'HR Executive', '2020-06-01', 5, 16, 3),
(20, 'Victoria Eng', '1995-01-20', 72000, '20 Tech Park', 'Engineer', '2021-02-01', 6, 2, 1),
(21, 'Zoe Eng', '1996-02-14', 71000, '21 Lab St', 'Engineer', '2021-04-01', 6, 2, 1),
(22, 'Michael Sales', '1994-08-08', 67000, '22 Shop Ave', 'Sales Executive', '2021-03-01', 5, 9, 2),
(23, 'Daniel Sales', '1995-10-10', 66000, '23 Mall Rd', 'Sales Executive', '2021-06-01', 5, 10, 2),
(24, 'Matthew HR', '1996-12-12', 62000, '24 People Ln', 'HR Assistant', '2021-07-01', 4, 17, 3),
(25, 'Andrew Eng', '1997-03-03', 70000, '25 Circuit Dr', 'Engineer', '2021-08-01', 6, 2, 1);

-- Set department heads now that employees exist
UPDATE department SET head_employee_id = 2 WHERE id = 1; -- Emma EngHead for Engineering
UPDATE department SET head_employee_id = 9 WHERE id = 2; -- Mia SalesHead for Sales
UPDATE department SET head_employee_id = 15 WHERE id = 3; -- Evelyn HRHead for HR

-- For convenience, ensure manager foreign keys exist (they were set inline). All done.
