package com.example.emsismartpresence;

public class AbsenceRecord {
    private String profId;
    private String studentName;
    private String timestamp;
    private String groupeNom;

    public AbsenceRecord() {}

    public AbsenceRecord(String profId, String studentName, String timestamp, String groupeNom) {
        this.profId = profId;
        this.studentName = studentName;
        this.timestamp = timestamp;
        this.groupeNom = groupeNom;
    }

    public String getProfId() {
        return profId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getGroupeNom() {
        return groupeNom;
    }
}