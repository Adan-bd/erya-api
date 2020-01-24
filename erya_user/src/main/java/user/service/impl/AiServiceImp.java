package user.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import user.service.AiService;
import user.vo.Text;
import user.vo.Token;
import user.vo.Voice;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiServiceImp implements AiService {
    private String token;
    private RestTemplate restTemplate;

    public AiServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getText(InputStream inputStream) {
        if (token == null)
            getToken();
        byte[] bytes = new byte[0];
        try {
            bytes = mp3Convertpcm(new BufferedInputStream(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Text text = baiduApi(bytes);
        if (text == null)
            return null;
        if (text.getErr_no() == 3302) {
            getToken();
            text = baiduApi(bytes);
        }
        if (text == null)
            return null;
        if (text.getResult() != null)
            return text.getResult().get(0);
        return null;
    }

    private Text baiduApi(byte[] bytes) {
        Voice voice = new Voice();
        voice.setToken(token);
        voice.setChannel(1);
        String mac = null;
        try {
            mac = getLocalMac().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mac == null)
            voice.setCuid("134.175.99.101");
        else voice.setCuid(mac);
        voice.setRate(16000);
        voice.setFormat("pcm");
        voice.setLen(bytes.length);
        voice.setSpeech(Base64.getEncoder().encodeToString(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Voice> formEntity = new HttpEntity<>(voice, headers);
        String reqText = "http://vop.baidu.com/server_api";
        ResponseEntity<Text> responseEntity = restTemplate.postForEntity(reqText, formEntity, Text.class);
        return responseEntity.getBody();
    }

    private void getToken() {
        String reqToken = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=X2TAxnjxGa1SMfX9oQ2Q9MTv&client_secret=GEmG3rTxyu3iQNaATAG7mY87fiyBKf59";
        Token token = restTemplate.postForObject(reqToken, null, Token.class);
        if (token != null)
            this.token = token.getAccess_token();
    }

    private byte[] mp3Convertpcm(InputStream mp3Stream) throws Exception {
        // 原MP3文件转AudioInputStream
        AudioInputStream mp3audioStream = AudioSystem.getAudioInputStream(mp3Stream);
        // 将AudioInputStream MP3文件 转换为PCM AudioInputStream
        AudioInputStream pcmaudioStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED,
                mp3audioStream);
        byte[] pcmBytes = IOUtils.toByteArray(pcmaudioStream);
        pcmaudioStream.close();
        mp3audioStream.close();
        return pcmBytes;
    }

    private List<String> getLocalMac() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        while (en.hasMoreElements()) {
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (InterfaceAddress addr : addrs) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                sb.delete(0, sb.length());
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                tmpMacList.add(sb.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        //去重，别忘了同一个网卡的ipv4,ipv6得到的mac都是一样的，肯定有重复，下面这段代码是。。流式处理
        return tmpMacList.stream().distinct().collect(Collectors.toList());
    }
}
