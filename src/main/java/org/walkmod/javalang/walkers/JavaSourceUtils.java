package org.walkmod.javalang.walkers;

import java.io.File;
import java.io.IOException;

import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.PackageDeclaration;
import org.walkmod.javalang.ast.expr.NameExpr;

public class JavaSourceUtils {

    public static String getSourceDirs(String rootDir, File file, CompilationUnit cu) throws IOException,
            InvalidSourceDirectoryException {
        String readerFile = new File(rootDir).getCanonicalPath();
        if (!readerFile.endsWith(File.separator)) {
            readerFile += File.separator;
        }

        String path = file.getCanonicalPath();
        String cuPath = getRelativePath(file, cu);

        int srcIndex = path.indexOf(cuPath);
        if (srcIndex < 0) {
            throw new InvalidSourceDirectoryException();
        }
        return path.substring(readerFile.length(), srcIndex);
    }

    public static String getRelativePath(File file, CompilationUnit cu) throws IOException {
      
        String cuPath = "";

        PackageDeclaration pd = cu.getPackage();

        if (pd != null) {
            NameExpr packageName = pd.getName();
            String packPath = packageName.toString().replace('.', File.separatorChar);
            cuPath = packPath + File.separator + file.getName();
        } else {
            cuPath = file.getName();
        }
        return cuPath;
    }

}
