package com.xht.android.managerhelp.mode;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */

public class OrderPictBean {


    /**
     * process_file : [{"fileId":73,"checkStatus":"审核通过","upTime":"2017-0104 11:30:34","file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1483500634108_bzzbz_o3_s4_e8_f1_t1483500631936_g.jpg","employeeName":"覃源源"}]
     * flowName : 资料交接
     * result_file : [{"fileId":74,"checkStatus":"审核通过","upTime":"2017-0104 11:30:50","file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1483500650236_bzzbz_o3_s4_e8_f1_t1483500647887_j.jpg","employeeName":"覃源源"}]
     */

    private String flowName;
    private List<ProcessFileBean> process_file;
    private List<ResultFileBean> result_file;

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public List<ProcessFileBean> getProcess_file() {
        return process_file;
    }

    public void setProcess_file(List<ProcessFileBean> process_file) {
        this.process_file = process_file;
    }

    public List<ResultFileBean> getResult_file() {
        return result_file;
    }

    public void setResult_file(List<ResultFileBean> result_file) {
        this.result_file = result_file;
    }

    public static class ProcessFileBean {
        /**
         * fileId : 73
         * checkStatus : 审核通过
         * upTime : 2017-0104 11:30:34
         * file : http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1483500634108_bzzbz_o3_s4_e8_f1_t1483500631936_g.jpg
         * employeeName : 覃源源
         */

        private String fileId;
        private String checkStatus;
        private String upTime;
        private String file;
        private String employeeName;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }
    }

    public static class ResultFileBean {
        /**
         * fileId : 74
         * checkStatus : 审核通过
         * upTime : 2017-0104 11:30:50
         * file : http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1483500650236_bzzbz_o3_s4_e8_f1_t1483500647887_j.jpg
         * employeeName : 覃源源
         */

        private String fileId;
        private String checkStatus;
        private String upTime;
        private String file;
        private String employeeName;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }
    }
}
