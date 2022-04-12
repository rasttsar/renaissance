package org.renaissance.huffman

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;

final class Huffman {
    private static class Node implements Comparable<Node>{
        public Byte character;
        public int count, order;
        public Node left;
        public Node right;

        public Node(byte character, int count, Node left, Node right)
        {
            this.character = character;
            this.count = count;
            this.left = left;
            this.right = right;
            this.order = 0;
        }

        public Node(int count, Node left, Node right,int order)
        {
            this.character = null;
            this.count = count;
            this.left = left;
            this.right = right;
            this.order = order;
        }

        public int compareTo(Node otherNode)
        {
            if (this.character != null)
            {
                if (otherNode.character == null)
                {
                    if (this.count > otherNode.count) return 1;
                    return -1;
                }
                else
                {
                    if (this.count > otherNode.count) return 1;
                    if (this.count < otherNode.count) return -1;
                    if (this.count == otherNode.count)
                    {
                        if (this.character > otherNode.character) return 1;
                        if (this.character < otherNode.character) return -1;
                        return 0;
                    }
                }
            }
            else
            {
                if (otherNode.character != null)
                {
                    if (this.count < otherNode.count) return -1;
                    return 1;
                }
                else
                {
                    if (this.count > otherNode.count) return 1;
                    if (this.count < otherNode.count) return -1;
                    if (this.count == otherNode.count)
                    {
                        if (this.order > otherNode.order) return 1;
                        if (this.order < otherNode.order) return -1;
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    private static class HuffmanReader
    {
        private InputStream stream;
        public HuffmanReader(InputStream _stream)
        {
            stream = _stream;
        }

        public int[] readFile()
        {
            int[] huffmanArray = new int[256];
            try
            {
                int c = stream.read();
                while (c != -1){
                    huffmanArray[c]++;
                    c = stream.read();
                }
                return huffmanArray;
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }

    private static Node createTreeFromArray(int[] huffmanArray){
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < huffmanArray.length; ++i){
            Integer ii = i;
            nodeList.add(new Node(ii.byteValue(),huffmanArray[i], null, null));
        }
        Collections.sort(nodeList);
        int i = 0;
        while (nodeList.size() != 1){
            Node left = nodeList.get(0);
            Node right = nodeList.get(1);
            Node innerNode = new Node(left.count + right.count, left, right, i);
            ++i;
            nodeList.remove(0);
            nodeList.remove(1);
            int j = 0;
            for(; j < nodeList.size(); ++j){
                if (innerNode.compareTo(nodeList.get(j)) == 1){
                    break;
                }
            }
            nodeList.add(j, innerNode);
        }
        return nodeList.get(0);
    }

    public Node run(){
        Node node = null;
        try(
                InputStream s = requireNonNull(Huffman.class.getResourceAsStream("shakespeare.txt"))) {
            var huffmanReader = new HuffmanReader((s));
            int[] huffmanArray = huffmanReader.readFile();
            return createTreeFromArray(huffmanArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}