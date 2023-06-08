package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {

        MethodSignature methodSignature = new MethodSignature(signatureString);

        String[] parts = signatureString.split(" ");

        if(parts.length > 1 && isAccessModifier(parts[0])){
            methodSignature.setAccessModifier(parts[0]);
        }

        int returnTypeIndex = methodSignature.getAccessModifier() != null ? 1 : 0;
        methodSignature.setReturnType(parts[returnTypeIndex]);

        int methodNameIndex = returnTypeIndex + 1;
        String methodName = trimStringUntilCharacter(parts[methodNameIndex], '(') ;
        methodSignature.setMethodName(methodName);

        String argumentsString = extractArgumentString(signatureString);
        List<MethodSignature.Argument> arguments = parseArguments(argumentsString);
        methodSignature.setArguments(arguments);


        return methodSignature;
    }

    private boolean isAccessModifier(String part) {
        return part.equals("public") || part.equals("private") || part.equals("protected");
    }

        public String trimStringUntilCharacter(String string, char character) {
            int index = string.indexOf(character);
            if (index != -1) {
                string = string.substring(0, index);
            }
            return string;
        }
    private String extractArgumentString(String signatureString){
        int startIndex = signatureString.indexOf('(');
        int endIndex = signatureString.indexOf(')');
        if(startIndex != -1 && endIndex != -1 && endIndex > startIndex){
            return signatureString.substring(startIndex + 1, endIndex).trim();
        }
        return "";
    }
    private List< MethodSignature.Argument> parseArguments(String argumentsString) {
        List< MethodSignature.Argument> arguments = new ArrayList<>();
        if (argumentsString.isEmpty()) {
            return arguments;
        }

        String[] argumentTokens = argumentsString.split(",");
        for (String argumentToken : argumentTokens) {
            String[] argumentParts = argumentToken.trim().split(" ");
            if (argumentParts.length >= 2) {
                String type = argumentParts[0];
                String name = argumentParts[1];
                arguments.add(new MethodSignature.Argument(type, name));
            }
        }

        return arguments;
    }

    public static void main(String[] args) {
        MethodParser  methodParser = new MethodParser();
       MethodSignature ms = methodParser.parseFunction("private void log(String logString, LogLevel level, Context context)");
        System.out.println("This is accessModifier: " + ms.getAccessModifier() );
        System.out.println("This is returnType: " + ms.getReturnType());
        System.out.println("This is MethodName: " + ms.getMethodName());
        System.out.println("These are args: " + ms.getArguments());

    }

}
