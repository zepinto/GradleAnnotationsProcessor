package pt.lsts.imc4j.processor;

import com.google.auto.service.AutoService;
import pt.lsts.imc4j.annotations.ErrorProne;
import pt.lsts.imc4j.annotations.Publishes;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.*;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PublishesProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element e : roundEnv.getElementsAnnotatedWith(Publishes.class)) {
            Element publishEl = processingEnv.getElementUtils().getTypeElement(Publishes.class.getName());
            TypeMirror publishType = publishEl.asType();

            e.getAnnotationMirrors().forEach(am -> {
                if (am.getAnnotationType().equals(publishType)) {
                    am.getElementValues().entrySet().forEach(el -> {
                        if (el.getKey().getSimpleName().toString().equals("value")) {
                            String trimmed = el.getValue().toString().replaceAll("\\{", "")
                                    .replaceAll("\\}", "").trim();
                            if (trimmed.isEmpty())
                                return;

                            List<String> excepts = Arrays.asList(trimmed.split(",")).stream()
                                    .map(s -> s.substring(0, s.length() - 6)).collect(Collectors.toList());

                            System.out.println(e.getSimpleName() +" publishes "+ excepts);
                        }
                    });
                }
            });
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Publishes.class.getCanonicalName());
    }
}