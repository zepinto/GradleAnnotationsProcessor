package pt.lsts.imc4j.processor;

import com.google.auto.service.AutoService;
import pt.lsts.imc4j.annotations.ErrorProne;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ErrorProneProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<String> annotatedClasses = new HashSet<>();

        for (Element e : roundEnv.getElementsAnnotatedWith(ErrorProne.class)) {
            processElement(e, annotatedClasses);
        }
        return true;
    }

    private void processElement(Element e, Set<String> annotatedClasses){
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "This method is error prone", e);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ErrorProne.class.getCanonicalName());
    }
}
