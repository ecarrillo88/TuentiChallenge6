/**
 * 
 * Tuenti Challenge 6
 * Challenge 3 - YATM Microservice
 * @author Enrique Carrillo (@ecarrillo88)
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class YATMmicroservice {
    
    static class TreeNode {
        String value;
        TreeNode parent;
        List<TreeNode> children;
        
        public TreeNode(String value, TreeNode parent) {
            this.value = value;
            this.parent = parent;
            this.children = new ArrayList<TreeNode>();
        }
        
        public TreeNode(String value, TreeNode parent, ArrayList<TreeNode> children) {
            this.value = value;
            this.parent = parent;
            this.children = children;
        }
    }
    
    private static String executeTape(TreeNode code, ArrayList<String> tape) {
        ArrayList<String> tapeTmp = tape;
        int tapeIndex = 0;
        List<TreeNode> stateList = code.children;
        List<TreeNode> symbolList = null;
        List<TreeNode> actionList = null;
        TreeNode state = null;
        TreeNode symbol = null;
        TreeNode action = null;
        String nextState = "start";
        boolean jumpToNextState;
        while (!nextState.equals("end")) {
            jumpToNextState = false;
            for (int i = 0; i < stateList.size() && !jumpToNextState; i++) {
                state = stateList.get(i);
                if (state.value.equals(nextState)) {
                    symbolList = state.children;
                    for (int j = 0; j < symbolList.size() && !jumpToNextState; j++) {
                        symbol = symbolList.get(j);
                        if (symbol.value.equals(tapeTmp.get(tapeIndex))) {
                            actionList = symbol.children;
                            action = actionList.get(0);
                            if (action != null) {
                                tapeTmp.set(tapeIndex, action.value);
                            }
                            action = actionList.get(1);
                            if (action != null) {
                                if (action.value.equals("right")) {
                                    tapeIndex++;
                                    if (tapeIndex >= tapeTmp.size()) {
                                        tapeTmp.add(" ");
                                    }
                                } else {
                                    tapeIndex--;
                                }
                            }
                            action = actionList.get(2);
                            if (action != null) {
                                nextState = action.value;
                                jumpToNextState = true;
                            }
                        }
                    }
                }
            }   
        }
        
        String result = "";
        for (String element : tapeTmp) {
            result += element;
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Skip first line
        
        TreeNode code = null;
        TreeNode function = null;
        TreeNode symbol = null;
        String[] action = null;
        ArrayList<ArrayList<String>> tapes = new ArrayList<ArrayList<String>>();
        
        boolean isCode = false;
        boolean isTape = false;
        
        String line;
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            if (line.equals("...")) {
                break;
            }
            
            if (line.equals("code:")) {
                isCode = true;
                code = new TreeNode(line, null);
                line = scanner.nextLine();
            }
            
            if (line.equals("tapes:")) {
                isCode = false;
                isTape = true;
                line = scanner.nextLine();
            }
            
            // Read machine code
            if (isCode) {
                String value = line.replaceAll("^\\s+","");
                int blanks = line.length() - value.length();
                
                // funtion
                if(blanks == 2) {
                    function = new TreeNode(value.substring(0, value.length()-1), code);
                    code.children.add(function);
                }
                // symbol
                else if(blanks == 4) {
                    ArrayList<TreeNode> emptyActions = new ArrayList<TreeNode>(3);
                    emptyActions.add(null);  // write
                    emptyActions.add(null);  // move
                    emptyActions.add(null);  // state
                    symbol = new TreeNode(value.substring(1, 2), function, emptyActions);
                    function.children.add(symbol);
                }
                // actions
                else if(blanks == 6) {
                    action = value.split(": ");
                    action[1] = action[1].replace("'", "");
                    if (action[0].equals("write")) {
                        symbol.children.set(0, new TreeNode(action[1], symbol));
                    }
                    if (action[0].equals("move")) {
                        symbol.children.set(1, new TreeNode(action[1], symbol));
                    }
                    if (action[0].equals("state")) {
                        symbol.children.set(2, new TreeNode(action[1], symbol));
                    }
                }
            }
            
            // Read tapes
            if (isTape) {
                line = line.trim();
                line = line.substring(4, line.length()-1);
                ArrayList<String> tape = new ArrayList<String>(Arrays.asList(line.split("")));
                tapes.add(tape);
            }
        }
        
        // Execute machine code and print final state for each tape
        for (int i = 1; i <= tapes.size(); i++) {
            String finalState = executeTape(code, tapes.get(i - 1));
            System.out.println("Tape #" + i + ": " + finalState);
        }
    }

}
