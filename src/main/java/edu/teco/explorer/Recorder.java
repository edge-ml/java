package edu.teco.explorer;

import org.json.JSONException;
import org.json.JSONObject;

public class Recorder {

    private final String UPLOADDATASETURL = "/api/deviceapi/uploadDataset";

    private String backendUrl;
    private String projectKey;

    /**
     * Class for recording datasets and sending them to the Explorer
     * @param backendUrl The backend-URL of the explorer
     * @param key The deviceApiKey
     */
    public Recorder(String backendUrl, String key) {
        this.backendUrl = backendUrl;
        this.projectKey = key;
    }

    /**
     * Upload datasets in increments
     * @param useDeviceTime True if you don't want to provide own timestamps
     * @param name The name of the dataset
     * @return An object to record the dataset incrementally
     * @throws Exception Failed to create dataset in backend
     */
    public IncrementalRecorder getIncrementalDataset(String name, boolean useDeviceTime) throws Exception {
        if (!useDeviceTime) {
            return new IncrementalRecorderUserTime(this.backendUrl, projectKey, name);
        }
        else {
            return new IncrementalRecorderDeviceTime(this.backendUrl, projectKey, name);
        }
    }


    /**
     * Sends a whole dataset to the backend-server
     * @param dataset The dataset to send
     * @return true if the send was successful, false otherwise
     */
    public boolean sendDataset(JSONObject dataset) {
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("key", this.projectKey);
            sendObj.put("payload", dataset);
            JSONObject ret = new NetworkCommunicator(this.backendUrl + this.UPLOADDATASETURL).sendPost( sendObj);
            return ret != null && ret.getInt("STATUS") == 200;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
