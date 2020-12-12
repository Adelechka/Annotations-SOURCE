import com.google.auto.service.AutoService;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm", "HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);

            configuration.setTemplateLoader(new FileTemplateLoader(new File("D:\\Projects\\JavaLab\\15.Annotations_SOURCE\\src\\main\\resources")));
            Template template = configuration.getTemplate("template_for_form.ftlh");

            // получить типы с аннотаций HtmlForm
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
            HashMap<String, Object> attributes = new HashMap<>();
            for (Element element : annotatedElements) {
                List<Input> inputs = new LinkedList<>();
                // получаем полный путь для генерации html
                String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                // User.class -> User.html
                path = path.substring(1) + element.getSimpleName().toString() + ".html";
                Path out = Paths.get(path);
                FileWriter fileWriter = new FileWriter(String.valueOf(out));
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                attributes.put("method", annotation.method());
                attributes.put("action", annotation.action());
                Set<? extends Element> annotatedFields = roundEnv.getElementsAnnotatedWith(HtmlInput.class);
                for (Element element1 : annotatedFields) {
                    if (element.getSimpleName().toString().equals(element1.getEnclosingElement().toString())) {
                        HtmlInput annotation1 = element1.getAnnotation(HtmlInput.class);
                        Input input = Input.builder()
                                .type(annotation1.type())
                                .name(annotation1.name())
                                .placeholder(annotation1.placeholder())
                                .build();
                        inputs.add(input);
                    }
                }
                attributes.put("inputs", inputs);
                template.process(attributes, fileWriter);
            }
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
        return true;
    }
}
