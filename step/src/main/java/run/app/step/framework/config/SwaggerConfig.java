package run.app.step.framework.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.app.step.common.constants.Constants;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lingSong
 * @date 2020/8/15 16:19
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /** 是否开启swagger*/
    @Value("${swagger.enabled}")
    private boolean enabled;

    /** 设置请求的统一前缀 */
    @Value("${swagger.pathMapping}")
    private String pathMapping;

    @Bean
    public Docket createRestAapi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                //设置swagger测试用request头信息
                .globalOperationParameters(ceratePars())
                //设置暴露那些接口
                .select()
                //扫描所有有注解的api
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //可设置扫描那些包
                .paths(PathSelectors.any())
                .build()
                .pathMapping(pathMapping);
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：Step管理系统_接口文档")
                // 描述
                .description("描述：目前用于查看后端功能性")
                // 作者信息
                .contact(new Contact("rookieTalent", "https://github.com/RookieTalent", "2544472820@qq.com"))
                // 版本
                .version("版本号:" + "0.0.1")
                .build();
    }


    /**
     * 临时测试用
     * @return
     */
    private List<Parameter> ceratePars(){
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder Authorization = new ParameterBuilder();

        Authorization.name(Constants.ACCESS_TOKEN)
                .description("swagger测试用(模拟access_token)非必填 header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false);

        parameters.add(Authorization.build());

        return parameters;
    }
}
