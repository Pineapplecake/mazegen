import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class MazeGen {
    public static void main(String[] args) {
        // set maze dimensions
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the dimensions of the maze, e.g. '16x24': ");
        String[] dim = sc.next().split("x");
        int m = 0;
        int n = 0;
        try {
            m = Integer.parseInt(dim[0]);
            n = Integer.parseInt(dim[1]);
            if(m < 1 || n < 1) {
                throw new Exception();
            }
        } catch(Exception e) {
            System.out.println("Please input two positive integers separated by an 'x'.");
            System.exit(1);
        }
        // show unsolved maze
        MazeGen mg = new MazeGen();
        Cell[][] maze = mg.genMaze(m, n);
        mg.drawMaze(maze, "");
        System.out.println();
        // solve maze
        System.out.println("Give up? Press 'Enter' to show the maze solution.");
        sc.nextLine();
        sc.nextLine();
        sc.close();
        String path = mg.solveMaze(maze);
        System.out.println("Solved maze with path: " + path);
        System.out.println();
        mg.drawMaze(maze, path);
    }

    // generates a random m by n maze with Prim's algorithm
    public Cell[][] genMaze(int m, int n) {
        Random rand = new Random();
        Cell[][] maze = new Cell[m][n];
        for(int r = 0; r < m; ++r) {
            for(int c = 0; c < n; ++c) {
                maze[r][c] = new Cell(r, c);
            }
        }
        int inactiveCellCount = m*n;
        LinkedList<Cell> activeCells = new LinkedList<Cell>();
        // activate first cell
        //activeCells.add(maze[m/2][n/2]);
        activeCells.add(maze[rand.nextInt(m)][rand.nextInt(n)]);
        --inactiveCellCount;

        while(inactiveCellCount > 0) {
            LinkedList<Cell> newActiveCells = new LinkedList<Cell>();
            for(Cell c : activeCells) {
                switch(rand.nextInt(4)) {
                    // connect north
                    case 0:
                        if(c.row > 0 && !maze[c.row-1][c.col].isActive()) {
                            c.nWall = false;
                            maze[c.row-1][c.col].sWall = false;
                            newActiveCells.add(maze[c.row-1][c.col]);
                            --inactiveCellCount;
                        }
                        break;
                    // connect south
                    case 1:
                        if(c.row < m-1 && !maze[c.row+1][c.col].isActive()) {
                            c.sWall = false;
                            maze[c.row+1][c.col].nWall = false;
                            newActiveCells.add(maze[c.row+1][c.col]);
                            --inactiveCellCount;
                        }
                        break;
                    // connect east
                    case 2:
                        if(c.col < n-1 && !maze[c.row][c.col+1].isActive()) {
                            c.eWall = false;
                            maze[c.row][c.col+1].wWall = false;
                            newActiveCells.add(maze[c.row][c.col+1]);
                            --inactiveCellCount;
                        }
                        break;
                    // connect west
                    case 3:
                        if(c.col > 0 && !maze[c.row][c.col-1].isActive()) {
                            c.wWall = false;
                            maze[c.row][c.col-1].eWall = false;
                            newActiveCells.add(maze[c.row][c.col-1]);
                            --inactiveCellCount;
                        }
                        break;
                }
            }
            activeCells.addAll(newActiveCells);
        }
        return maze;
    }

    // finds the shortest path through the given maze
    public String solveMaze(Cell[][] maze) {
        int m = maze.length;
        int n = maze[0].length;

        Queue<PathStep> paths = new LinkedList<PathStep>();
        paths.add(new PathStep(maze[0][0], ""));
        while(!paths.isEmpty()) {
            PathStep ps = paths.remove();
            // maze end found
            if(ps.cell.row == m-1 && ps.cell.col == n-1) {
                return ps.path;
            }
            char lastMove = ps.path.isEmpty() ? ' ' : ps.path.charAt(ps.path.length()-1);
            // search north
            if(lastMove != 'S' && !ps.cell.nWall) {
                paths.add(new PathStep(maze[ps.cell.row-1][ps.cell.col], ps.path + "N"));
            }
            // search south
            if(lastMove != 'N' && !ps.cell.sWall) {
                paths.add(new PathStep(maze[ps.cell.row+1][ps.cell.col], ps.path + "S"));
            }
            // search east
            if(lastMove != 'W' && !ps.cell.eWall) {
                paths.add(new PathStep(maze[ps.cell.row][ps.cell.col+1], ps.path + "E"));
            }
            // search west
            if(lastMove != 'E' && !ps.cell.wWall) {
                paths.add(new PathStep(maze[ps.cell.row][ps.cell.col-1], ps.path + "W"));
            }
        }

        return "";
    }

    // draws a maze
    // shows the solution if path is not an empty string
    public void drawMaze(Cell[][] maze, String path) {
        int m = maze.length;
        int n = maze[0].length;
        char[] wallTileSet = {' ', '╵', '╷', '│', '╶', '└', '┌', '├', '╴', '┘', '┐', '┤', '─', '┴', '┬', '┼'};

        char[][] picture = new char[2*m+1][2*n+1];
        for(int r = 0; r < 2*m+1; ++r) {
            for(int c = 0; c < 2*n+1; ++c) {
                int wallType = 0;
                // draw corners
                if(r % 2 == 0 && c % 2 == 0) {
                    // corner north wall
                    if(r > 0 && (c == 0 || c == 2*n || maze[(r/2)-1][(c/2)-1].eWall)) {
                        wallType |= 1;
                    }
                    // corner south wall
                    if(r < 2*m && (c == 0 || c == 2*n || maze[r/2][c/2].wWall)) {
                        wallType |= 2;
                    }
                    // corner east wall
                    if(c < 2*n && (r == 0 || r == 2*m || maze[r/2][c/2].nWall)) {
                        wallType |= 4;
                    }
                    // corner west wall
                    if(c > 0 && (r == 0 || r == 2*m || maze[(r/2)-1][(c/2)-1].sWall)) {
                        wallType |= 8;
                    }
                // draw horizontal edges
                } else if(r % 2 == 0 && c % 2 != 0) {
                    if(r == 0 || maze[(r/2)-1][(c-1)/2].sWall) {
                        wallType = 0xC;
                    }
                // draw vertical edges
                } else if(r % 2 != 0 && c % 2 == 0) {
                    if(c == 0 || maze[(r-1)/2][(c/2)-1].eWall) {
                        wallType = 0x3;
                    }
                }
                // clear openings for beginning and end
                if(r == 0) {
                    if(c == 0) {
                        wallType = 0x2;
                    } else if(c == 1) {
                        wallType = 0x0;
                    } else if(c == 2) {
                        wallType &= 0x7;
                    }
                } else if(r == 2*m) {
                    if(c == 2*n) {
                        wallType = 0x1;
                    } else if(c == 2*n-1) {
                        wallType = 0x0;
                    } else if(c == 2*n-2) {
                        wallType &= 0xB;
                    }
                }
                // set wall characters
                picture[r][c] = wallTileSet[wallType];
            }
        }
        // trace path
        if(!path.equals("")) {
            int r = 0;
            int c = 1;
            // trace start
            picture[r][c] = '█';
            ++r;
            picture[r][c] = '█';
            for(char ch : path.toCharArray()) {
                switch(ch) {
                    case 'N':
                        --r;
                        picture[r][c] = '█';
                        --r;
                        picture[r][c] = '█';
                        break;
                    case 'S':
                        ++r;
                        picture[r][c] = '█';
                        ++r;
                        picture[r][c] = '█';
                        break;
                    case 'E':
                        ++c;
                        picture[r][c] = '█';
                        ++c;
                        picture[r][c] = '█';
                        break;
                    case 'W':
                        --c;
                        picture[r][c] = '█';
                        --c;
                        picture[r][c] = '█';
                        break;
                }
            }
            // trace end
            ++r;
            picture[r][c] = '█';
        }
        // print picture
        for(int r = 0; r < picture.length; ++r) {
            for(int c = 0; c < picture[0].length; ++c) {
                // print intersections and vertical lines normally
                if(c % 2 == 0) {
                    System.out.print(picture[r][c]);
                // lengthen horizontal lines a bit
                } else {
                    // fix path width
                    if(picture[r][c] == '█') {
                        System.out.print((picture[r][c-1] == '█') ? '█' : '▐');
                        System.out.print(picture[r][c]);
                        System.out.print((picture[r][c+1] == '█') ? '█' : '▌');
                    } else {
                        System.out.print(picture[r][c]);
                        System.out.print(picture[r][c]);
                        System.out.print(picture[r][c]);
                    }
                }
            }
            System.out.println();
        }
    }

    class Cell {
        // cell index
        public int row, col;
        // whether each wall of the cell is present
        public boolean nWall = true;
        public boolean sWall = true;
        public boolean eWall = true;
        public boolean wWall = true;

        public Cell(int r, int c) {
            this.row = r;
            this.col = c;
        }

        public boolean isActive() {
            return !(this.nWall && this.sWall && this.eWall && this.wWall);
        }
    }

    class PathStep {
        public Cell cell;
        public String path;
        
        public  PathStep(Cell c, String p) {
            this.cell = c;
            this.path = p;
        }
    }
}
