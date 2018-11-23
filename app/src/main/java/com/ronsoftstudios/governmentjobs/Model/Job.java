package com.ronsoftstudios.governmentjobs.Model;

import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Ronsoft on 11/7/2017.
 */

public class Job implements Serializable{

        private String jobId;
        private String jobPositionTitle;
        private String jobOrganization;
        private String jobRateIntervalCode;
        private String jobMimimumPay;
        private String jobMaximumPay;
        private String jobStartApplicationDate;
        private String jobEndApplicationDate;
        private String[] jobLocations;
        private String jobUrl;

       public Job(){}

    public Job(String jobId) {
        this.jobId = jobId;
    }

    public Job(String jobId, String jobPositionTitle, String jobOrganization, String jobRateIntervalCode, String jobMimimumPay, String jobMaximumPay, String jobStartApplicationDate, String jobEndApplicationDate, String[] jobLocations, String jobUrl) {
            this.jobId = jobId;
            this.jobPositionTitle = jobPositionTitle;
            this.jobOrganization = jobOrganization;
            this.jobRateIntervalCode = jobRateIntervalCode;
            this.jobMimimumPay = jobMimimumPay;
            this.jobMaximumPay = jobMaximumPay;
            this.jobStartApplicationDate = jobStartApplicationDate;
            this.jobEndApplicationDate = jobEndApplicationDate;
            this.jobLocations = jobLocations;
            this.jobUrl = jobUrl;
        }


        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getJobPositionTitle() {
            return jobPositionTitle;
        }

        public void setJobPositionTitle(String jobPositionTitle) {
            this.jobPositionTitle = jobPositionTitle;
        }

        public String getJobOrganization() {
            return jobOrganization;
        }

        public void setJobOrganization(String jobOrganization) {
            this.jobOrganization = jobOrganization;
        }

        public String getJobRateIntervalCode() {
            return jobRateIntervalCode;
        }

        public void setJobRateIntervalCode(String jobRateIntervalCode) {
            this.jobRateIntervalCode = jobRateIntervalCode;
        }

        public String getJobMimimumPay() {
            return jobMimimumPay;
        }

        public void setJobMimimumPay(String jobMimimumPay) {
            this.jobMimimumPay = jobMimimumPay;
        }

        public String getJobMaximumPay() {
            return jobMaximumPay;
        }

        public void setJobMaximumPay(String jobMaximumPay) {
            this.jobMaximumPay = jobMaximumPay;
        }

        public String getJobStartApplicationDate() {
            return jobStartApplicationDate;
        }

        public void setJobStartApplicationDate(String jobStartApplicationDate) {
            this.jobStartApplicationDate = jobStartApplicationDate;
        }

        public String getJobEndApplicationDate() {
            return jobEndApplicationDate;
        }

        public void setJobEndApplicationDate(String jobEndApplicationDate) {
            this.jobEndApplicationDate = jobEndApplicationDate;
        }

        public String[] getJobLocations() {
            return jobLocations;
        }

        public void setJobLocations(String[] jobLocations) {
            this.jobLocations = jobLocations;
        }

        public String getJobUrl() {
            return jobUrl;
        }

        public void setJobUrl(String jobUrl) {
            this.jobUrl = jobUrl;
        }
}
