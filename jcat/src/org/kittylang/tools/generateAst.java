package org.kittylang.tools;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class generateAst {

    public static void main(String [] args) throws IOException {
        // a small cmd line tool to generate ast classes
        if (args.length!=1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir=args[0];
        defineAst(
                outputDir,"Expr", Arrays.asList(
                        "Binary : Expr left, Token operator, Expr right",
                        "Grouping : Expr expression",
                        "Literal : Object value",
                        "Unary : Token operator, Expr right"
                )
        );
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path=outputDir+"/"+baseName+".java";
        PrintWriter writer=new PrintWriter(path,"UTF-8");
        writer.println("package org.kittylang.jcat;");
        writer.println("import java.util.List;");
        writer.println("//This File has been programatically generated  from org.kittylang.tools.generateAst");
        writer.println("abstract class "+baseName+"{");

        for(String type:types){
            String className=type.split(":")[0].trim();
            String fields=type.split(":")[1].trim();
            defineType(writer,baseName,className,fields);
        }

        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fields) {

        writer.println(" static class "+className+" extends "+baseName+"{");
        writer.println(className+"("+fields+"){");
        String[] field=fields.split(", ");
        for (String f:field){
            String name=f.split(" ")[1];
            writer.println("this."+name+"="+name+";");

        }
        writer.println("}");
        for (String f:field){

            writer.println("final "+f+";");

        }
        writer.println("}");
    }
}
