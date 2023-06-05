-- Necessary to clear the database
PRAGMA writable_schema = 1;
DELETE FROM sqlite_master;
PRAGMA writable_schema = 0;
VACUUM;
PRAGMA integrity_check;

CREATE TABLE IF NOT EXISTS Departments(
    name        TEXT PRIMARY KEY,
    abbr        TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Subjects(
    name        TEXT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Programs(
    name        TEXT PRIMARY KEY,
    abbr        TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Courses(
    code        TEXT PRIMARY KEY,
    name        TEXT NOT NULL,
    credits     FLOAT NOT NULL,
    level       TEXT NOT NULL,
    link        TEXT NOT NULL,
    CONSTRAINT positive_credit CHECK(credits>0)
);

CREATE TABLE IF NOT EXISTS HeldIn(
    course TEXT,
    period INT CHECK (period IN (0,1,2,3,4)),
    PRIMARY KEY (course, period)
);

CREATE TABLE IF NOT EXISTS HostedBy(
    department  TEXT,
    program     TEXT,
    PRIMARY KEY (department, program),
    FOREIGN KEY (department) REFERENCES Departments ON UPDATE CASCADE,
    FOREIGN KEY (program) REFERENCES Programs ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS GivenBy(
    course      TEXT,
    department  TEXT,
    PRIMARY KEY (course, department),
    FOREIGN KEY (course)  REFERENCES Courses ON UPDATE CASCADE,
    FOREIGN KEY (department) REFERENCES Departments ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS MandatoryProgram(
    course      TEXT,
    program     TEXT,
    PRIMARY KEY (course, program),
    FOREIGN KEY (course) REFERENCES Courses ON UPDATE CASCADE,
    FOREIGN KEY (program) REFERENCES Programs ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Prerequisite(
    needCourse  TEXT,
    forCourse   TEXT,
    PRIMARY KEY (needCourse, forCourse),
    FOREIGN KEY (needCourse) REFERENCES Courses ON UPDATE CASCADE,
    FOREIGN KEY (forCourse) REFERENCES Courses ON UPDATE CASCADE
);