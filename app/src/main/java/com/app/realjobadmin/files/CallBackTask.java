package com.app.realjobadmin.files;

interface CallBackTask {
    void PickerManagerOnUriReturned();

    void PickerManagerOnPreExecute();

    void PickerManagerOnProgressUpdate(int progress);

    void PickerManagerOnPostExecute(String path, boolean wasDriveFile, boolean wasSuccessful, String reason);
}
