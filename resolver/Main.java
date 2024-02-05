
class Main {

    public static void main(String[] args) throws Exception{
        JSFile jsFile = new JSFile("./src/main.js");
        jsFile.resolveImports(RelativeModulesResolver.class);
        jsFile.writeOut("./dist");
        System.out.println(RelativeModulesResolver.getInstance().getModuleMap());
    }
}