package www.sh.com.web;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author liaojinneng
 * @date 2018/8/1
 */
@Slf4j
@RestController
@RequestMapping("/qrCode")
@Api(value = "qrCode", description = "二维码")
public class QrCodeController {

    @ApiOperation(value = "根据输入的信息生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",value="登录凭证",dataType="String",paramType="header",required = true),
    })
    @RequestMapping(value = "/generatorQrCode", method = RequestMethod.POST)
    public ModelAndView generatorQrCode(HttpServletRequest request, HttpServletResponse response,
                                        @ApiParam(name = "qrText",value="用于生成二维码的信息", required = true) @RequestParam("qrText") String qrText) {

        OutputStream outStream = null;
        try {
            ByteArrayOutputStream out = QRCode.from(qrText).to(ImageType.PNG).stream();
            response.setContentType("image/png");
            response.setContentLength(out.size());
            outStream = response.getOutputStream();
            outStream.write(out.toByteArray());
            outStream.flush();
        } catch (IOException e) {
            log.error("QrCodeController generatorQrCode has an IOException: ",e);
        } finally {
            try {
                if(outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
               log.error("QrCodeController generatorQrCode has an IOException: ",e);
            }
        }

        return new ModelAndView("qrCode");

    }

}
