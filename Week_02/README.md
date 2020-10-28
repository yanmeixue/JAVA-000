

## 周四作业一
使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例

- java -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

```
正在执行...
执行结束!共生成对象次数:10405
```
```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4739280k free), swap 27104284k(5652304k free)
CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 
2020-10-29T01:57:07.741+0800: 0.488: [GC (Allocation Failure) [PSYoungGen: 524795K->87039K(611840K)] 524795K->137172K(2010112K), 0.0260164 secs] [Times: user=0.05 sys=0.14, real=0.03 secs] 
2020-10-29T01:57:07.880+0800: 0.626: [GC (Allocation Failure) [PSYoungGen: 611839K->87034K(611840K)] 661972K->246436K(2010112K), 0.0394194 secs] [Times: user=0.08 sys=0.16, real=0.04 secs] 
2020-10-29T01:57:08.017+0800: 0.764: [GC (Allocation Failure) [PSYoungGen: 611834K->87032K(611840K)] 771236K->372498K(2010112K), 0.0450089 secs] [Times: user=0.14 sys=0.09, real=0.05 secs] 
2020-10-29T01:57:08.182+0800: 0.929: [GC (Allocation Failure) [PSYoungGen: 611832K->87023K(611840K)] 897298K->498855K(2010112K), 0.0446713 secs] [Times: user=0.14 sys=0.03, real=0.05 secs] 
2020-10-29T01:57:08.354+0800: 1.101: [GC (Allocation Failure) [PSYoungGen: 611823K->87026K(611840K)] 1023655K->612889K(2010112K), 0.0554926 secs] [Times: user=0.08 sys=0.09, real=0.05 secs] 
Heap
 PSYoungGen      total 611840K, used 262993K [0x00000000d5580000, 0x0000000100000000, 0x0000000100000000)
  eden space 524800K, 33% used [0x00000000d5580000,0x00000000e0157b70,0x00000000f5600000)
  from space 87040K, 99% used [0x00000000f5600000,0x00000000faafc898,0x00000000fab00000)
  to   space 87040K, 0% used [0x00000000fab00000,0x00000000fab00000,0x0000000100000000)
 ParOldGen       total 1398272K, used 525863K [0x0000000080000000, 0x00000000d5580000, 0x00000000d5580000)
  object space 1398272K, 37% used [0x0000000080000000,0x00000000a0189e68,0x00000000d5580000)
 Metaspace       used 3998K, capacity 4572K, committed 4864K, reserved 1056768K
  class space    used 442K, capacity 460K, committed 512K, reserved 1048576K

```
**默认并行GC，不指定Xmx时，可以看到默认最大堆内存给了四分之一系统内存。由于初始堆较小，full GC 3次**

---

- java -Xloggc:gc.log -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis


```
正在执行...
执行结束!共生成对象次数:10773
```

```
CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
2020-10-28T21:55:04.456-0800: 0.407: [GC (Allocation Failure) [PSYoungGen: 524800K->87035K(611840K)] 524800K->137787K(2010112K), 0.0545506 secs] [Times: user=0.06 sys=0.11, real=0.06 secs] 
2020-10-28T21:55:04.601-0800: 0.551: [GC (Allocation Failure) [PSYoungGen: 611835K->87039K(611840K)] 662587K->242910K(2010112K), 0.0875817 secs] [Times: user=0.09 sys=0.16, real=0.08 secs] 
2020-10-28T21:55:04.795-0800: 0.746: [GC (Allocation Failure) [PSYoungGen: 611742K->87038K(611840K)] 767612K->361270K(2010112K), 0.0602489 secs] [Times: user=0.09 sys=0.08, real=0.06 secs] 
2020-10-28T21:55:04.932-0800: 0.883: [GC (Allocation Failure) [PSYoungGen: 611838K->87038K(611840K)] 886070K->464862K(2010112K), 0.0517437 secs] [Times: user=0.08 sys=0.07, real=0.05 secs] 
2020-10-28T21:55:05.050-0800: 1.001: [GC (Allocation Failure) [PSYoungGen: 611838K->87037K(611840K)] 989662K->576210K(2010112K), 0.0514836 secs] [Times: user=0.09 sys=0.09, real=0.05 secs] 
Heap
 PSYoungGen      total 611840K, used 303274K [0x0000000795580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 524800K, 41% used [0x0000000795580000,0x00000007a28ab480,0x00000007b5600000)
  from space 87040K, 99% used [0x00000007b5600000,0x00000007baaff418,0x00000007bab00000)
  to   space 87040K, 0% used [0x00000007bab00000,0x00000007bab00000,0x00000007c0000000)
 ParOldGen       total 1398272K, used 489173K [0x0000000740000000, 0x0000000795580000, 0x0000000795580000)
  object space 1398272K, 34% used [0x0000000740000000,0x000000075ddb57a0,0x0000000795580000)
 Metaspace       used 2716K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 297K, capacity 386K, committed 512K, reserved 1048576K

```
**默认并行GC，指定Xms=Xmx=2G，可以看到无fullGC，设置效果明显**

---

- java -XX:+UseSerialGC -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

```
正在执行...
执行结束!共生成对象次数:7177
```

```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4610252k free), swap 27104284k(5669088k free)
CommandLine flags: -XX:InitialHeapSize=267123840 -XX:MaxHeapSize=4273981440 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseSerialGC 
2020-10-29T01:59:28.922+0800: 0.255: [GC (Allocation Failure) 2020-10-29T01:59:28.923+0800: 0.255: [DefNew: 69952K->8703K(78656K), 0.0165341 secs] 69952K->23853K(253440K), 0.0167185 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-29T01:59:28.961+0800: 0.294: [GC (Allocation Failure) 2020-10-29T01:59:28.961+0800: 0.294: [DefNew: 78507K->8700K(78656K), 0.0181004 secs] 93656K->50039K(253440K), 0.0181597 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
2020-10-29T01:59:28.996+0800: 0.329: [GC (Allocation Failure) 2020-10-29T01:59:28.996+0800: 0.329: [DefNew: 78448K->8702K(78656K), 0.0155865 secs] 119788K->72060K(253440K), 0.0156866 secs] [Times: user=0.00 sys=0.02, real=0.02 secs] 
2020-10-29T01:59:29.030+0800: 0.363: [GC (Allocation Failure) 2020-10-29T01:59:29.030+0800: 0.363: [DefNew: 78654K->8703K(78656K), 0.0142761 secs] 142012K->93790K(253440K), 0.0143704 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-29T01:59:29.064+0800: 0.396: [GC (Allocation Failure) 2020-10-29T01:59:29.064+0800: 0.396: [DefNew: 78497K->8703K(78656K), 0.0156864 secs] 163584K->118564K(253440K), 0.0157514 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-29T01:59:29.099+0800: 0.432: [GC (Allocation Failure) 2020-10-29T01:59:29.099+0800: 0.432: [DefNew: 78655K->8703K(78656K), 0.0126751 secs] 188516K->138484K(253440K), 0.0127514 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-29T01:59:29.128+0800: 0.460: [GC (Allocation Failure) 2020-10-29T01:59:29.128+0800: 0.460: [DefNew: 78655K->8703K(78656K), 0.0151425 secs] 208436K->161689K(253440K), 0.0152233 secs] [Times: user=0.00 sys=0.02, real=0.01 secs] 
2020-10-29T01:59:29.202+0800: 0.534: [GC (Allocation Failure) 2020-10-29T01:59:29.202+0800: 0.534: [DefNew: 78587K->8703K(78656K), 0.0168495 secs]2020-10-29T01:59:29.219+0800: 0.551: [Tenured: 177430K->163640K(177480K), 0.0351815 secs] 231572K->163640K(256136K), [Metaspace: 3511K->3511K(1056768K)], 0.0527006 secs] [Times: user=0.03 sys=0.01, real=0.05 secs] 
2020-10-29T01:59:29.294+0800: 0.627: [GC (Allocation Failure) 2020-10-29T01:59:29.294+0800: 0.627: [DefNew: 109120K->13626K(122752K), 0.0164279 secs] 272760K->203573K(395488K), 0.0164895 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-29T01:59:29.333+0800: 0.666: [GC (Allocation Failure) 2020-10-29T01:59:29.333+0800: 0.666: [DefNew: 122734K->13631K(122752K), 0.0324070 secs] 312681K->237659K(395488K), 0.0324852 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 
2020-10-29T01:59:29.393+0800: 0.726: [GC (Allocation Failure) 2020-10-29T01:59:29.393+0800: 0.726: [DefNew: 122751K->13631K(122752K), 0.0289770 secs] 346779K->275676K(395488K), 0.0290504 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-29T01:59:29.449+0800: 0.782: [GC (Allocation Failure) 2020-10-29T01:59:29.449+0800: 0.782: [DefNew: 122751K->13630K(122752K), 0.0215581 secs]2020-10-29T01:59:29.471+0800: 0.804: [Tenured: 292805K->245922K(292940K), 0.0469819 secs] 384796K->245922K(415692K), [Metaspace: 3511K->3511K(1056768K)], 0.0691595 secs] [Times: user=0.05 sys=0.02, real=0.07 secs] 
2020-10-29T01:59:29.559+0800: 0.892: [GC (Allocation Failure) 2020-10-29T01:59:29.559+0800: 0.892: [DefNew: 164032K->20479K(184512K), 0.0174272 secs] 409954K->303243K(594384K), 0.0174974 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-29T01:59:29.608+0800: 0.941: [GC (Allocation Failure) 2020-10-29T01:59:29.608+0800: 0.941: [DefNew: 184511K->20479K(184512K), 0.0342095 secs] 467275K->351432K(594384K), 0.0342935 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-29T01:59:29.676+0800: 1.009: [GC (Allocation Failure) 2020-10-29T01:59:29.676+0800: 1.009: [DefNew: 184346K->20479K(184512K), 0.0338817 secs] 515299K->398930K(594384K), 0.0340201 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2020-10-29T01:59:29.744+0800: 1.077: [GC (Allocation Failure) 2020-10-29T01:59:29.744+0800: 1.077: [DefNew: 184511K->20479K(184512K), 0.0357618 secs]2020-10-29T01:59:29.781+0800: 1.114: [Tenured: 428034K->321068K(428088K), 0.0704438 secs] 562962K->321068K(612600K), [Metaspace: 3511K->3511K(1056768K)], 0.1087163 secs] [Times: user=0.08 sys=0.02, real=0.11 secs] 
Heap
 def new generation   total 240832K, used 13012K [0x00000006c1400000, 0x00000006d1950000, 0x00000007162a0000)
  eden space 214080K,   6% used [0x00000006c1400000, 0x00000006c20b50c8, 0x00000006ce510000)
  from space 26752K,   0% used [0x00000006ce510000, 0x00000006ce510000, 0x00000006cff30000)
  to   space 26752K,   0% used [0x00000006cff30000, 0x00000006cff30000, 0x00000006d1950000)
 tenured generation   total 535116K, used 321068K [0x00000007162a0000, 0x0000000736d33000, 0x00000007c0000000)
   the space 535116K,  59% used [0x00000007162a0000, 0x0000000729c2b3c0, 0x0000000729c2b400, 0x0000000736d33000)
 Metaspace       used 3525K, capacity 4502K, committed 4864K, reserved 1056768K
  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K

```

---
- java -XX:+UseSerialGC -Xms2048m -Xmx2048m -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

```
正在执行...
执行结束!共生成对象次数:6078
```

```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4569500k free), swap 27104284k(5600516k free)
CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseSerialGC 
2020-10-29T02:00:45.461+0800: 0.788: [GC (Allocation Failure) 2020-10-29T02:00:45.461+0800: 0.788: [DefNew: 559232K->69888K(629120K), 0.0801364 secs] 559232K->147333K(2027264K), 0.0803673 secs] [Times: user=0.06 sys=0.01, real=0.08 secs] 
2020-10-29T02:00:45.671+0800: 0.998: [GC (Allocation Failure) 2020-10-29T02:00:45.671+0800: 0.998: [DefNew: 629120K->69887K(629120K), 0.1106453 secs] 706565K->284412K(2027264K), 0.1107202 secs] [Times: user=0.05 sys=0.06, real=0.11 secs] 
2020-10-29T02:00:45.904+0800: 1.232: [GC (Allocation Failure) 2020-10-29T02:00:45.904+0800: 1.232: [DefNew: 629119K->69888K(629120K), 0.0824450 secs] 843644K->410678K(2027264K), 0.0825238 secs] [Times: user=0.05 sys=0.03, real=0.08 secs] 
Heap
 def new generation   total 629120K, used 86548K [0x0000000080000000, 0x00000000aaaa0000, 0x00000000aaaa0000)
  eden space 559232K,   2% used [0x0000000080000000, 0x0000000081045028, 0x00000000a2220000)
  from space 69888K, 100% used [0x00000000a6660000, 0x00000000aaaa0000, 0x00000000aaaa0000)
  to   space 69888K,   0% used [0x00000000a2220000, 0x00000000a2220000, 0x00000000a6660000)
 tenured generation   total 1398144K, used 340790K [0x00000000aaaa0000, 0x0000000100000000, 0x0000000100000000)
   the space 1398144K,  24% used [0x00000000aaaa0000, 0x00000000bf76da88, 0x00000000bf76dc00, 0x0000000100000000)
 Metaspace       used 4011K, capacity 4572K, committed 4864K, reserved 1056768K
  class space    used 443K, capacity 460K, committed 512K, reserved 1048576K
```

---
- java -Xloggc:gc.log -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

```
正在执行...
执行结束!共生成对象次数:8940
```

```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4529704k free), swap 27104284k(5593780k free)
CommandLine flags: -XX:InitialHeapSize=267123840 -XX:MaxHeapSize=4273981440 -XX:MaxNewSize=697933824 -XX:MaxTenuringThreshold=6 -XX:OldPLABSize=16 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 
2020-10-29T02:01:41.471+0800: 0.239: [GC (Allocation Failure) 2020-10-29T02:01:41.471+0800: 0.239: [ParNew: 69804K->8704K(78656K), 0.0046641 secs] 69804K->21354K(253440K), 0.0048313 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:01:41.498+0800: 0.266: [GC (Allocation Failure) 2020-10-29T02:01:41.499+0800: 0.266: [ParNew: 78525K->8704K(78656K), 0.0077226 secs] 91176K->42800K(253440K), 0.0078068 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.522+0800: 0.290: [GC (Allocation Failure) 2020-10-29T02:01:41.522+0800: 0.290: [ParNew: 78655K->8700K(78656K), 0.0136901 secs] 112752K->66754K(253440K), 0.0137751 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.549+0800: 0.317: [GC (Allocation Failure) 2020-10-29T02:01:41.549+0800: 0.317: [ParNew: 78652K->8695K(78656K), 0.0107641 secs] 136706K->86783K(253440K), 0.0108505 secs] [Times: user=0.08 sys=0.02, real=0.01 secs] 
2020-10-29T02:01:41.573+0800: 0.341: [GC (Allocation Failure) 2020-10-29T02:01:41.573+0800: 0.341: [ParNew: 78647K->8689K(78656K), 0.0115319 secs] 156735K->107537K(253440K), 0.0115912 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.585+0800: 0.353: [GC (CMS Initial Mark) [1 CMS-initial-mark: 98847K(174784K)] 107904K(253440K), 0.0002106 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:01:41.585+0800: 0.353: [CMS-concurrent-mark-start]
2020-10-29T02:01:41.587+0800: 0.355: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:01:41.587+0800: 0.355: [CMS-concurrent-preclean-start]
2020-10-29T02:01:41.588+0800: 0.356: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:01:41.588+0800: 0.356: [CMS-concurrent-abortable-preclean-start]
2020-10-29T02:01:41.599+0800: 0.367: [GC (Allocation Failure) 2020-10-29T02:01:41.599+0800: 0.367: [ParNew: 78641K->8702K(78656K), 0.0132664 secs] 177489K->131877K(253440K), 0.0133440 secs] [Times: user=0.08 sys=0.02, real=0.01 secs] 
2020-10-29T02:01:41.625+0800: 0.393: [GC (Allocation Failure) 2020-10-29T02:01:41.625+0800: 0.393: [ParNew: 78477K->8702K(78656K), 0.0128213 secs] 201652K->153855K(253440K), 0.0128975 secs] [Times: user=0.08 sys=0.02, real=0.01 secs] 
2020-10-29T02:01:41.655+0800: 0.422: [GC (Allocation Failure) 2020-10-29T02:01:41.655+0800: 0.422: [ParNew: 78359K->8701K(78656K), 0.0116751 secs] 223511K->174515K(253440K), 0.0118218 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.683+0800: 0.451: [GC (Allocation Failure) 2020-10-29T02:01:41.683+0800: 0.451: [ParNew: 78653K->8698K(78656K), 0.0124885 secs] 244467K->196180K(266864K), 0.0125657 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.710+0800: 0.478: [GC (Allocation Failure) 2020-10-29T02:01:41.710+0800: 0.478: [ParNew: 78066K->8702K(78656K), 0.0132460 secs] 265549K->219007K(289664K), 0.0133265 secs] [Times: user=0.06 sys=0.03, real=0.01 secs] 
2020-10-29T02:01:41.738+0800: 0.506: [GC (Allocation Failure) 2020-10-29T02:01:41.738+0800: 0.506: [ParNew: 78483K->8701K(78656K), 0.0151429 secs] 288787K->245057K(315840K), 0.0152264 secs] [Times: user=0.05 sys=0.03, real=0.01 secs] 
2020-10-29T02:01:41.765+0800: 0.533: [GC (Allocation Failure) 2020-10-29T02:01:41.765+0800: 0.533: [ParNew: 78653K->8703K(78656K), 0.0131672 secs] 315009K->268430K(339092K), 0.0132562 secs] [Times: user=0.05 sys=0.03, real=0.01 secs] 
2020-10-29T02:01:41.791+0800: 0.560: [GC (Allocation Failure) 2020-10-29T02:01:41.791+0800: 0.560: [ParNew: 78595K->8702K(78656K), 0.0117852 secs] 338322K->288788K(359568K), 0.0119217 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.817+0800: 0.586: [GC (Allocation Failure) 2020-10-29T02:01:41.817+0800: 0.586: [ParNew: 78654K->8703K(78656K), 0.0160629 secs] 358740K->315810K(386468K), 0.0161522 secs] [Times: user=0.05 sys=0.02, real=0.02 secs] 
2020-10-29T02:01:41.848+0800: 0.617: [GC (Allocation Failure) 2020-10-29T02:01:41.848+0800: 0.617: [ParNew: 78655K->8702K(78656K), 0.0134038 secs] 385762K->338578K(409324K), 0.0134619 secs] [Times: user=0.06 sys=0.03, real=0.01 secs] 
2020-10-29T02:01:41.874+0800: 0.643: [GC (Allocation Failure) 2020-10-29T02:01:41.874+0800: 0.643: [ParNew: 78495K->8701K(78656K), 0.0162905 secs] 408371K->367193K(437888K), 0.0163590 secs] [Times: user=0.06 sys=0.03, real=0.02 secs] 
2020-10-29T02:01:41.904+0800: 0.672: [GC (Allocation Failure) 2020-10-29T02:01:41.904+0800: 0.672: [ParNew: 78539K->8700K(78656K), 0.0141840 secs] 437031K->390592K(461364K), 0.0142846 secs] [Times: user=0.09 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:41.934+0800: 0.702: [GC (Allocation Failure) 2020-10-29T02:01:41.934+0800: 0.702: [ParNew: 78652K->8703K(78656K), 0.0163326 secs] 460544K->418276K(489116K), 0.0164324 secs] [Times: user=0.08 sys=0.01, real=0.02 secs] 
2020-10-29T02:01:41.965+0800: 0.733: [GC (Allocation Failure) 2020-10-29T02:01:41.965+0800: 0.733: [ParNew: 78604K->8701K(78656K), 0.0142383 secs] 488178K->442160K(513044K), 0.0143387 secs] [Times: user=0.05 sys=0.02, real=0.01 secs] 
2020-10-29T02:01:41.991+0800: 0.760: [GC (Allocation Failure) 2020-10-29T02:01:41.991+0800: 0.760: [ParNew: 78125K->8701K(78656K), 0.0161106 secs] 511584K->469428K(540372K), 0.0162132 secs] [Times: user=0.08 sys=0.00, real=0.02 secs] 
2020-10-29T02:01:42.023+0800: 0.791: [GC (Allocation Failure) 2020-10-29T02:01:42.023+0800: 0.791: [ParNew: 78653K->8700K(78656K), 0.0155196 secs] 539380K->495130K(566116K), 0.0156291 secs] [Times: user=0.08 sys=0.00, real=0.02 secs] 
2020-10-29T02:01:42.057+0800: 0.826: [GC (Allocation Failure) 2020-10-29T02:01:42.057+0800: 0.826: [ParNew: 78652K->8702K(78656K), 0.0135750 secs] 565082K->518700K(589704K), 0.0136676 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:42.087+0800: 0.856: [GC (Allocation Failure) 2020-10-29T02:01:42.087+0800: 0.856: [ParNew: 78178K->8703K(78656K), 0.0141884 secs] 588175K->541974K(612904K), 0.0142727 secs] [Times: user=0.08 sys=0.02, real=0.02 secs] 
2020-10-29T02:01:42.120+0800: 0.889: [GC (Allocation Failure) 2020-10-29T02:01:42.120+0800: 0.889: [ParNew: 78529K->8699K(78656K), 0.0111427 secs] 611801K->560693K(631628K), 0.0112250 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:42.147+0800: 0.915: [GC (Allocation Failure) 2020-10-29T02:01:42.147+0800: 0.915: [ParNew: 78401K->8698K(78656K), 0.0126992 secs] 630395K->581807K(652800K), 0.0127890 secs] [Times: user=0.09 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:42.173+0800: 0.941: [GC (Allocation Failure) 2020-10-29T02:01:42.173+0800: 0.941: [ParNew: 78637K->8703K(78656K), 0.0132449 secs] 651746K->604945K(675880K), 0.0133327 secs] [Times: user=0.09 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:42.202+0800: 0.970: [GC (Allocation Failure) 2020-10-29T02:01:42.202+0800: 0.970: [ParNew: 78655K->8701K(78656K), 0.0133918 secs] 674897K->626416K(697400K), 0.0134911 secs] [Times: user=0.05 sys=0.03, real=0.01 secs] 
2020-10-29T02:01:42.230+0800: 0.998: [GC (Allocation Failure) 2020-10-29T02:01:42.230+0800: 0.998: [ParNew: 78653K->8701K(78656K), 0.0140064 secs] 696368K->649880K(720964K), 0.0140801 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:01:42.259+0800: 1.027: [GC (Allocation Failure) 2020-10-29T02:01:42.259+0800: 1.028: [ParNew: 78653K->8703K(78656K), 0.0154444 secs] 719832K->676880K(747848K), 0.0155207 secs] [Times: user=0.06 sys=0.00, real=0.02 secs] 
2020-10-29T02:01:42.291+0800: 1.060: [GC (Allocation Failure) 2020-10-29T02:01:42.291+0800: 1.060: [ParNew: 78655K->8701K(78656K), 0.0149149 secs] 746832K->702834K(773756K), 0.0149922 secs] [Times: user=0.00 sys=0.00, real=0.02 secs] 
2020-10-29T02:01:42.320+0800: 1.088: [GC (Allocation Failure) 2020-10-29T02:01:42.320+0800: 1.088: [ParNew: 78653K->8703K(78656K), 0.0154070 secs] 772786K->729437K(800576K), 0.0154847 secs] [Times: user=0.09 sys=0.00, real=0.02 secs] 
2020-10-29T02:01:42.350+0800: 1.118: [GC (Allocation Failure) 2020-10-29T02:01:42.350+0800: 1.118: [ParNew: 78487K->8702K(78656K), 0.0159001 secs] 799221K->753885K(824920K), 0.0159908 secs] [Times: user=0.08 sys=0.02, real=0.02 secs] 
2020-10-29T02:01:42.380+0800: 1.148: [GC (Allocation Failure) 2020-10-29T02:01:42.380+0800: 1.148: [ParNew: 78654K->8701K(78656K), 0.0131935 secs] 823837K->775268K(846520K), 0.0132799 secs] [Times: user=0.08 sys=0.01, real=0.01 secs] 
2020-10-29T02:01:42.409+0800: 1.177: [GC (Allocation Failure) 2020-10-29T02:01:42.409+0800: 1.177: [ParNew: 78438K->8697K(78656K), 0.0134847 secs] 845004K->796952K(868168K), 0.0135662 secs] [Times: user=0.08 sys=0.00, real=0.01 secs] 
Heap
 par new generation   total 78656K, used 11456K [0x00000006c1400000, 0x00000006c6950000, 0x00000006ead90000)
  eden space 69952K,   3% used [0x00000006c1400000, 0x00000006c16b19c0, 0x00000006c5850000)
  from space 8704K,  99% used [0x00000006c5850000, 0x00000006c60ce730, 0x00000006c60d0000)
  to   space 8704K,   0% used [0x00000006c60d0000, 0x00000006c60d0000, 0x00000006c6950000)
 concurrent mark-sweep generation total 789512K, used 788254K [0x00000006ead90000, 0x000000071b092000, 0x00000007c0000000)
 Metaspace       used 4007K, capacity 4572K, committed 4864K, reserved 1056768K
  class space    used 443K, capacity 460K, committed 512K, reserved 1048576K

```


---

- java -Xloggc:gc.log -XX:+UseConcMarkSweepGC -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

```
正在执行...
执行结束!共生成对象次数:10424
```

```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4771908k free), swap 27104284k(5624140k free)
CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:MaxNewSize=697933824 -XX:MaxTenuringThreshold=6 -XX:NewSize=697933824 -XX:OldPLABSize=16 -XX:OldSize=1395867648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 
2020-10-29T02:02:57.569+0800: 0.464: [GC (Allocation Failure) 2020-10-29T02:02:57.569+0800: 0.465: [ParNew: 545344K->68095K(613440K), 0.0254958 secs] 545344K->145544K(2029056K), 0.0257053 secs] [Times: user=0.05 sys=0.19, real=0.03 secs] 
2020-10-29T02:02:57.689+0800: 0.584: [GC (Allocation Failure) 2020-10-29T02:02:57.689+0800: 0.584: [ParNew: 613439K->68096K(613440K), 0.0329030 secs] 690888K->265040K(2029056K), 0.0329661 secs] [Times: user=0.09 sys=0.11, real=0.03 secs] 
2020-10-29T02:02:57.811+0800: 0.706: [GC (Allocation Failure) 2020-10-29T02:02:57.811+0800: 0.706: [ParNew: 613440K->68096K(613440K), 0.0725112 secs] 810384K->387842K(2029056K), 0.0725842 secs] [Times: user=0.31 sys=0.05, real=0.07 secs] 
2020-10-29T02:02:57.991+0800: 0.886: [GC (Allocation Failure) 2020-10-29T02:02:57.991+0800: 0.886: [ParNew: 613440K->68096K(613440K), 0.0783456 secs] 933186K->509794K(2029056K), 0.0784276 secs] [Times: user=0.36 sys=0.06, real=0.08 secs] 
2020-10-29T02:02:58.180+0800: 1.076: [GC (Allocation Failure) 2020-10-29T02:02:58.180+0800: 1.076: [ParNew: 613440K->68096K(613440K), 0.0826950 secs] 1055138K->629390K(2029056K), 0.0827801 secs] [Times: user=0.50 sys=0.06, real=0.08 secs] 
Heap
 par new generation   total 613440K, used 139128K [0x0000000080000000, 0x00000000a9990000, 0x00000000a9990000)
  eden space 545344K,  13% used [0x0000000080000000, 0x000000008455e308, 0x00000000a1490000)
  from space 68096K, 100% used [0x00000000a5710000, 0x00000000a9990000, 0x00000000a9990000)
  to   space 68096K,   0% used [0x00000000a1490000, 0x00000000a1490000, 0x00000000a5710000)
 concurrent mark-sweep generation total 1415616K, used 561294K [0x00000000a9990000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 3650K, capacity 4540K, committed 4864K, reserved 1056768K
  class space    used 397K, capacity 428K, committed 512K, reserved 1048576K

```

---
- java -Xloggc:gc.log -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis


```
正在执行...
执行结束!共生成对象次数:6367
```

```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4703604k free), swap 27104284k(5577328k free)
CommandLine flags: -XX:InitialHeapSize=267123840 -XX:MaxHeapSize=4273981440 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation 
2020-10-29T02:04:01.288+0800: 0.279: [GC pause (G1 Evacuation Pause) (young), 0.0035098 secs]
   [Parallel Time: 3.0 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 278.9, Avg: 281.2, Max: 281.9, Diff: 2.9]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.8, Diff: 0.8, Sum: 1.1]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 0.5, Max: 2.0, Diff: 2.0, Sum: 4.0]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.6]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
      [GC Worker Total (ms): Min: 0.0, Avg: 0.7, Max: 3.0, Diff: 3.0, Sum: 6.0]
      [GC Worker End (ms): Min: 281.9, Avg: 281.9, Max: 281.9, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 12.0M(12.0M)->0.0B(20.0M) Survivors: 0.0B->2048.0K Heap: 13.2M(256.0M)->4772.8K(256.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.308+0800: 0.300: [GC pause (G1 Evacuation Pause) (young), 0.0021275 secs]
   [Parallel Time: 1.6 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 299.8, Avg: 299.8, Max: 299.9, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 1.1]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.3, Diff: 0.3, Sum: 0.3]
         [Processed Buffers: Min: 0, Avg: 0.4, Max: 1, Diff: 1, Sum: 3]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.9, Avg: 1.2, Max: 1.3, Diff: 0.4, Sum: 9.5]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 1.4, Avg: 1.5, Max: 1.6, Diff: 0.2, Sum: 12.0]
      [GC Worker End (ms): Min: 301.3, Avg: 301.3, Max: 301.3, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 20.0M(20.0M)->0.0B(99.0M) Survivors: 2048.0K->3072.0K Heap: 30.1M(256.0M)->13.5M(256.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.382+0800: 0.373: [GC pause (G1 Evacuation Pause) (young), 0.0065380 secs]
   [Parallel Time: 5.9 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 372.9, Avg: 373.1, Max: 373.5, Diff: 0.6]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.8]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.7]
         [Processed Buffers: Min: 0, Avg: 1.4, Max: 3, Diff: 3, Sum: 11]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 5.2, Avg: 5.4, Max: 5.5, Diff: 0.3, Sum: 43.2]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.9]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 5.3, Avg: 5.7, Max: 5.9, Diff: 0.6, Sum: 45.7]
      [GC Worker End (ms): Min: 378.8, Avg: 378.8, Max: 378.9, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 99.0M(99.0M)->0.0B(51.0M) Survivors: 3072.0K->13.0M Heap: 136.6M(256.0M)->57.1M(256.0M)]
 [Times: user=0.03 sys=0.06, real=0.01 secs] 
2020-10-29T02:04:01.404+0800: 0.395: [GC pause (G1 Evacuation Pause) (young), 0.0057652 secs]
   [Parallel Time: 5.2 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 394.9, Avg: 395.5, Max: 399.8, Diff: 4.9]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.7]
         [Processed Buffers: Min: 0, Avg: 1.4, Max: 2, Diff: 2, Sum: 11]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 4.1, Max: 4.8, Diff: 4.8, Sum: 32.6]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.3]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 0.2, Avg: 4.5, Max: 5.1, Diff: 5.0, Sum: 35.7]
      [GC Worker End (ms): Min: 400.0, Avg: 400.0, Max: 400.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 51.0M(51.0M)->0.0B(87.0M) Survivors: 13.0M->8192.0K Heap: 122.4M(256.0M)->84.3M(256.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:04:01.434+0800: 0.425: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0054901 secs]
   [Parallel Time: 4.6 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 425.2, Avg: 425.3, Max: 425.4, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.3, Max: 1.3, Diff: 1.2, Sum: 2.4]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.2, Diff: 0.2, Sum: 1.3]
         [Processed Buffers: Min: 0, Avg: 2.0, Max: 3, Diff: 3, Sum: 16]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 2.9, Avg: 3.8, Max: 4.1, Diff: 1.1, Sum: 30.0]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.7]
         [Termination Attempts: Min: 1, Avg: 1.8, Max: 3, Diff: 2, Sum: 14]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 4.3, Avg: 4.4, Max: 4.5, Diff: 0.2, Sum: 35.5]
      [GC Worker End (ms): Min: 429.7, Avg: 429.7, Max: 429.7, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.8 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 65.0M(87.0M)->0.0B(76.0M) Survivors: 8192.0K->12.0M Heap: 164.7M(256.0M)->107.6M(256.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:04:01.440+0800: 0.431: [GC concurrent-root-region-scan-start]
2020-10-29T02:04:01.440+0800: 0.431: [GC concurrent-root-region-scan-end, 0.0001884 secs]
2020-10-29T02:04:01.440+0800: 0.431: [GC concurrent-mark-start]
2020-10-29T02:04:01.442+0800: 0.434: [GC concurrent-mark-end, 0.0026165 secs]
2020-10-29T02:04:01.442+0800: 0.434: [GC remark 2020-10-29T02:04:01.443+0800: 0.434: [Finalize Marking, 0.0001136 secs] 2020-10-29T02:04:01.443+0800: 0.434: [GC ref-proc, 0.0001365 secs] 2020-10-29T02:04:01.443+0800: 0.434: [Unloading, 0.0005007 secs], 0.0011386 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.444+0800: 0.435: [GC cleanup 122M->122M(256M), 0.0004913 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.462+0800: 0.453: [GC pause (G1 Evacuation Pause) (young), 0.0064954 secs]
   [Parallel Time: 5.9 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 453.2, Avg: 453.3, Max: 453.7, Diff: 0.6]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
         [Processed Buffers: Min: 0, Avg: 1.6, Max: 3, Diff: 3, Sum: 13]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 5.2, Avg: 5.4, Max: 5.5, Diff: 0.3, Sum: 43.5]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.6]
         [Termination Attempts: Min: 1, Avg: 1.1, Max: 2, Diff: 1, Sum: 9]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 5.3, Avg: 5.8, Max: 5.9, Diff: 0.5, Sum: 46.2]
      [GC Worker End (ms): Min: 459.0, Avg: 459.0, Max: 459.1, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 76.0M(76.0M)->0.0B(59.0M) Survivors: 12.0M->11.0M Heap: 201.8M(256.0M)->140.8M(268.0M)]
 [Times: user=0.03 sys=0.08, real=0.01 secs] 
2020-10-29T02:04:01.469+0800: 0.460: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0023785 secs]
   [Parallel Time: 1.9 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 460.0, Avg: 460.0, Max: 460.2, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.5, Max: 1.7, Diff: 1.7, Sum: 4.2]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
         [Processed Buffers: Min: 0, Avg: 1.1, Max: 2, Diff: 2, Sum: 9]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 1.1, Max: 1.6, Diff: 1.6, Sum: 8.8]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.1]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 1.7, Avg: 1.8, Max: 1.8, Diff: 0.2, Sum: 14.3]
      [GC Worker End (ms): Min: 461.8, Avg: 461.8, Max: 461.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 1024.0K(59.0M)->0.0B(57.0M) Survivors: 11.0M->1024.0K Heap: 141.8M(268.0M)->140.0M(268.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.471+0800: 0.462: [GC concurrent-root-region-scan-start]
2020-10-29T02:04:01.471+0800: 0.462: [GC concurrent-root-region-scan-end, 0.0000411 secs]
2020-10-29T02:04:01.471+0800: 0.462: [GC concurrent-mark-start]
2020-10-29T02:04:01.474+0800: 0.465: [GC concurrent-mark-end, 0.0022829 secs]
2020-10-29T02:04:01.474+0800: 0.465: [GC remark 2020-10-29T02:04:01.474+0800: 0.465: [Finalize Marking, 0.0001443 secs] 2020-10-29T02:04:01.474+0800: 0.465: [GC ref-proc, 0.0002885 secs] 2020-10-29T02:04:01.474+0800: 0.465: [Unloading, 0.0005353 secs], 0.0014427 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.475+0800: 0.467: [GC cleanup 150M->149M(268M), 0.0004944 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.476+0800: 0.467: [GC concurrent-cleanup-start]
2020-10-29T02:04:01.476+0800: 0.467: [GC concurrent-cleanup-end, 0.0000126 secs]
2020-10-29T02:04:01.491+0800: 0.482: [GC pause (G1 Evacuation Pause) (young), 0.0034924 secs]
   [Parallel Time: 3.0 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 482.1, Avg: 482.8, Max: 485.0, Diff: 2.9]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.8]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.4]
         [Processed Buffers: Min: 0, Avg: 2.4, Max: 4, Diff: 4, Sum: 19]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 1.8, Max: 2.5, Diff: 2.5, Sum: 14.7]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 0.0, Avg: 2.2, Max: 2.9, Diff: 2.9, Sum: 17.4]
      [GC Worker End (ms): Min: 485.0, Avg: 485.0, Max: 485.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 57.0M(57.0M)->0.0B(5120.0K) Survivors: 1024.0K->8192.0K Heap: 211.3M(268.0M)->162.3M(268.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.496+0800: 0.488: [GC pause (G1 Evacuation Pause) (mixed), 0.0032295 secs]
   [Parallel Time: 2.6 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 487.6, Avg: 487.7, Max: 487.9, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
      [Update RS (ms): Min: 0.1, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 1.5]
         [Processed Buffers: Min: 1, Avg: 2.4, Max: 4, Diff: 3, Sum: 19]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.2, Diff: 0.2, Sum: 0.4]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.9, Avg: 2.0, Max: 2.1, Diff: 0.2, Sum: 16.0]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 2, Diff: 1, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 2.3, Avg: 2.5, Max: 2.6, Diff: 0.3, Sum: 19.7]
      [GC Worker End (ms): Min: 490.1, Avg: 490.2, Max: 490.2, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 5120.0K(5120.0K)->0.0B(46.0M) Survivors: 8192.0K->2048.0K Heap: 168.7M(268.0M)->154.6M(268.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.500+0800: 0.491: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0010549 secs]
   [Parallel Time: 0.6 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 491.2, Avg: 491.3, Max: 491.4, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.6]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.7]
         [Processed Buffers: Min: 0, Avg: 1.1, Max: 2, Diff: 2, Sum: 9]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 0.3, Avg: 0.4, Max: 0.5, Diff: 0.2, Sum: 3.3]
      [GC Worker End (ms): Min: 491.7, Avg: 491.7, Max: 491.7, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 1024.0K(46.0M)->0.0B(49.0M) Survivors: 2048.0K->2048.0K Heap: 155.1M(268.0M)->154.6M(268.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.501+0800: 0.492: [GC concurrent-root-region-scan-start]
2020-10-29T02:04:01.501+0800: 0.493: [GC concurrent-root-region-scan-end, 0.0002477 secs]
2020-10-29T02:04:01.501+0800: 0.493: [GC concurrent-mark-start]
2020-10-29T02:04:01.503+0800: 0.494: [GC concurrent-mark-end, 0.0018391 secs]
2020-10-29T02:04:01.503+0800: 0.495: [GC remark 2020-10-29T02:04:01.503+0800: 0.495: [Finalize Marking, 0.0001351 secs] 2020-10-29T02:04:01.504+0800: 0.495: [GC ref-proc, 0.0001445 secs] 2020-10-29T02:04:01.504+0800: 0.495: [Unloading, 0.0004981 secs], 0.0012842 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.505+0800: 0.496: [GC cleanup 162M->162M(268M), 0.0004812 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.519+0800: 0.510: [GC pause (G1 Evacuation Pause) (young), 0.0029695 secs]
   [Parallel Time: 2.4 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 510.5, Avg: 510.5, Max: 510.5, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 1.0]
      [Update RS (ms): Min: 0.2, Avg: 0.2, Max: 0.3, Diff: 0.1, Sum: 1.8]
         [Processed Buffers: Min: 3, Avg: 3.1, Max: 4, Diff: 1, Sum: 25]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.8, Avg: 1.8, Max: 1.9, Diff: 0.1, Sum: 14.8]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.5]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 2.3, Avg: 2.3, Max: 2.3, Diff: 0.1, Sum: 18.3]
      [GC Worker End (ms): Min: 512.8, Avg: 512.8, Max: 512.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 49.0M(49.0M)->0.0B(6144.0K) Survivors: 2048.0K->7168.0K Heap: 213.5M(268.0M)->169.8M(268.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.525+0800: 0.516: [GC pause (G1 Evacuation Pause) (mixed), 0.0100542 secs]
   [Parallel Time: 2.2 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 515.9, Avg: 516.0, Max: 516.1, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
      [Update RS (ms): Min: 0.1, Avg: 0.3, Max: 2.0, Diff: 1.9, Sum: 2.6]
         [Processed Buffers: Min: 0, Avg: 1.6, Max: 3, Diff: 3, Sum: 13]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 1.6, Max: 1.9, Diff: 1.9, Sum: 13.0]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 2.0, Avg: 2.1, Max: 2.2, Diff: 0.2, Sum: 17.1]
      [GC Worker End (ms): Min: 518.1, Avg: 518.1, Max: 518.1, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 7.7 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 6144.0K(6144.0K)->0.0B(225.0M) Survivors: 7168.0K->1024.0K Heap: 176.4M(268.0M)->165.3M(536.0M)]
 [Times: user=0.11 sys=0.00, real=0.01 secs] 
2020-10-29T02:04:01.633+0800: 0.624: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0198253 secs]
   [Parallel Time: 6.9 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 624.1, Avg: 624.2, Max: 624.2, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 1.1]
      [Update RS (ms): Min: 0.2, Avg: 0.2, Max: 0.2, Diff: 0.1, Sum: 1.7]
         [Processed Buffers: Min: 0, Avg: 2.6, Max: 3, Diff: 3, Sum: 21]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 6.3, Avg: 6.4, Max: 6.4, Diff: 0.1, Sum: 51.0]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 3, Diff: 2, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 6.8, Avg: 6.8, Max: 6.8, Diff: 0.1, Sum: 54.4]
      [GC Worker End (ms): Min: 631.0, Avg: 631.0, Max: 631.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 12.8 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 147.0M(225.0M)->0.0B(583.0M) Survivors: 1024.0K->29.0M Heap: 347.0M(536.0M)->222.1M(1072.0M)]
 [Times: user=0.05 sys=0.08, real=0.02 secs] 
2020-10-29T02:04:01.653+0800: 0.644: [GC concurrent-root-region-scan-start]
2020-10-29T02:04:01.653+0800: 0.644: [GC concurrent-root-region-scan-end, 0.0002138 secs]
2020-10-29T02:04:01.653+0800: 0.644: [GC concurrent-mark-start]
2020-10-29T02:04:01.655+0800: 0.646: [GC concurrent-mark-end, 0.0019579 secs]
2020-10-29T02:04:01.658+0800: 0.649: [GC remark 2020-10-29T02:04:01.658+0800: 0.649: [Finalize Marking, 0.0001314 secs] 2020-10-29T02:04:01.658+0800: 0.649: [GC ref-proc, 0.0001280 secs] 2020-10-29T02:04:01.658+0800: 0.649: [Unloading, 0.0005252 secs], 0.0013470 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.659+0800: 0.650: [GC cleanup 228M->227M(1072M), 0.0006972 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-29T02:04:01.660+0800: 0.651: [GC concurrent-cleanup-start]
2020-10-29T02:04:01.660+0800: 0.651: [GC concurrent-cleanup-end, 0.0000124 secs]
2020-10-29T02:04:02.039+0800: 1.030: [GC pause (G1 Evacuation Pause) (young), 0.0447490 secs]
   [Parallel Time: 30.0 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 1030.8, Avg: 1030.9, Max: 1031.6, Diff: 0.8]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.8]
      [Update RS (ms): Min: 0.0, Avg: 0.3, Max: 0.6, Diff: 0.6, Sum: 2.4]
         [Processed Buffers: Min: 0, Avg: 2.9, Max: 5, Diff: 5, Sum: 23]
      [Scan RS (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.5]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 28.8, Avg: 29.1, Max: 29.5, Diff: 0.6, Sum: 233.0]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 1.8]
         [Termination Attempts: Min: 1, Avg: 2.0, Max: 4, Diff: 3, Sum: 16]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.3]
      [GC Worker Total (ms): Min: 29.2, Avg: 29.8, Max: 30.0, Diff: 0.8, Sum: 238.8]
      [GC Worker End (ms): Min: 1060.8, Avg: 1060.8, Max: 1060.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 14.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.2 ms]
      [Humongous Reclaim: 0.3 ms]
      [Free CSet: 0.4 ms]
   [Eden: 583.0M(583.0M)->0.0B(440.0M) Survivors: 29.0M->77.0M Heap: 956.7M(1072.0M)->376.2M(1796.0M)]
 [Times: user=0.06 sys=0.09, real=0.05 secs] 
Heap
 garbage-first heap   total 1839104K, used 700332K [0x00000006c1400000, 0x00000006c1503820, 0x00000007c0000000)
  region size 1024K, 327 young (334848K), 77 survivors (78848K)
 Metaspace       used 4013K, capacity 4572K, committed 4864K, reserved 1056768K
  class space    used 443K, capacity 460K, committed 512K, reserved 1048576K

```


---

- java -Xloggc:gc.log -XX:+UseG1GC -Xms2048m -Xmx2048m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

```
正在执行...
执行结束!共生成对象次数:10248
```

```
Java HotSpot(TM) 64-Bit Server VM (25.161-b12) for windows-amd64 JRE (1.8.0_161-b12), built on Dec 19 2017 17:52:25 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 16695240k(4677540k free), swap 27104284k(5572300k free)
CommandLine flags: -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation 
2020-10-29T02:06:02.748+0800: 0.320: [GC pause (G1 Evacuation Pause) (young), 0.0067208 secs]
   [Parallel Time: 5.9 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 320.0, Avg: 320.0, Max: 320.0, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.3, Max: 0.8, Diff: 0.7, Sum: 2.3]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.6]
         [Processed Buffers: Min: 0, Avg: 2.9, Max: 4, Diff: 4, Sum: 23]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 4.7, Avg: 5.1, Max: 5.3, Diff: 0.6, Sum: 40.9]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.4]
      [GC Worker Total (ms): Min: 5.7, Avg: 5.7, Max: 5.8, Diff: 0.1, Sum: 46.0]
      [GC Worker End (ms): Min: 325.8, Avg: 325.8, Max: 325.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.7 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 102.0M(102.0M)->0.0B(89.0M) Survivors: 0.0B->13.0M Heap: 128.4M(2048.0M)->43.3M(2048.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:06:02.783+0800: 0.356: [GC pause (G1 Evacuation Pause) (young), 0.0084827 secs]
   [Parallel Time: 7.4 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 356.0, Avg: 356.5, Max: 357.6, Diff: 1.6]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.9]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.6]
         [Processed Buffers: Min: 0, Avg: 1.3, Max: 2, Diff: 2, Sum: 10]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 5.2, Avg: 6.3, Max: 6.9, Diff: 1.7, Sum: 50.0]
      [Termination (ms): Min: 0.0, Avg: 0.3, Max: 0.5, Diff: 0.5, Sum: 2.7]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 5.7, Avg: 6.8, Max: 7.3, Diff: 1.6, Sum: 54.5]
      [GC Worker End (ms): Min: 363.3, Avg: 363.3, Max: 363.3, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.0 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.3 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 89.0M(89.0M)->0.0B(89.0M) Survivors: 13.0M->13.0M Heap: 152.2M(2048.0M)->74.4M(2048.0M)]
 [Times: user=0.03 sys=0.03, real=0.01 secs] 
2020-10-29T02:06:02.838+0800: 0.410: [GC pause (G1 Evacuation Pause) (young), 0.0073003 secs]
   [Parallel Time: 6.5 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 410.6, Avg: 411.8, Max: 416.8, Diff: 6.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
         [Processed Buffers: Min: 0, Avg: 1.6, Max: 4, Diff: 4, Sum: 13]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 4.9, Max: 6.0, Diff: 6.0, Sum: 39.0]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.3, Diff: 0.3, Sum: 0.8]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 0.2, Avg: 5.2, Max: 6.5, Diff: 6.3, Sum: 41.6]
      [GC Worker End (ms): Min: 417.0, Avg: 417.0, Max: 417.0, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.7 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 89.0M(89.0M)->0.0B(89.0M) Survivors: 13.0M->13.0M Heap: 193.2M(2048.0M)->114.0M(2048.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:06:02.878+0800: 0.450: [GC pause (G1 Evacuation Pause) (young), 0.0092472 secs]
   [Parallel Time: 8.2 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 450.4, Avg: 453.0, Max: 456.3, Diff: 6.0]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.3, Diff: 0.3, Sum: 0.3]
      [Update RS (ms): Min: 0.0, Avg: 0.3, Max: 1.0, Diff: 1.0, Sum: 2.2]
         [Processed Buffers: Min: 0, Avg: 2.1, Max: 9, Diff: 9, Sum: 17]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.9, Avg: 4.9, Max: 6.9, Diff: 5.0, Sum: 39.6]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.7]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 3, Diff: 2, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 2.2, Avg: 5.5, Max: 8.1, Diff: 6.0, Sum: 44.0]
      [GC Worker End (ms): Min: 458.5, Avg: 458.5, Max: 458.6, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.9 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.3 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 89.0M(89.0M)->0.0B(100.0M) Survivors: 13.0M->13.0M Heap: 230.3M(2048.0M)->150.1M(2048.0M)]
 [Times: user=0.01 sys=0.06, real=0.01 secs] 
2020-10-29T02:06:02.919+0800: 0.492: [GC pause (G1 Evacuation Pause) (young), 0.0081461 secs]
   [Parallel Time: 7.2 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 491.7, Avg: 492.6, Max: 497.9, Diff: 6.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.8]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
         [Processed Buffers: Min: 0, Avg: 1.8, Max: 3, Diff: 3, Sum: 14]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.8, Avg: 5.8, Max: 6.8, Diff: 6.0, Sum: 46.6]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 3, Diff: 2, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.2]
      [GC Worker Total (ms): Min: 1.0, Avg: 6.2, Max: 7.2, Diff: 6.2, Sum: 49.9]
      [GC Worker End (ms): Min: 498.9, Avg: 498.9, Max: 498.9, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.8 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 100.0M(100.0M)->0.0B(129.0M) Survivors: 13.0M->15.0M Heap: 265.7M(2048.0M)->184.9M(2048.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:06:02.979+0800: 0.552: [GC pause (G1 Evacuation Pause) (young), 0.0104225 secs]
   [Parallel Time: 9.5 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 551.9, Avg: 552.1, Max: 552.8, Diff: 0.9]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.3]
         [Processed Buffers: Min: 0, Avg: 1.9, Max: 4, Diff: 4, Sum: 15]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 8.5, Avg: 8.9, Max: 9.0, Diff: 0.6, Sum: 71.1]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
      [GC Worker Total (ms): Min: 8.6, Avg: 9.3, Max: 9.5, Diff: 0.9, Sum: 74.4]
      [GC Worker End (ms): Min: 561.4, Avg: 561.4, Max: 561.4, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.8 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 129.0M(129.0M)->0.0B(216.0M) Survivors: 15.0M->18.0M Heap: 344.6M(2048.0M)->231.9M(2048.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-29T02:06:03.085+0800: 0.657: [GC pause (G1 Evacuation Pause) (young), 0.0139836 secs]
   [Parallel Time: 12.8 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 657.2, Avg: 657.3, Max: 657.6, Diff: 0.4]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.3]
         [Processed Buffers: Min: 0, Avg: 2.0, Max: 3, Diff: 3, Sum: 16]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 12.1, Avg: 12.2, Max: 12.3, Diff: 0.2, Sum: 97.9]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.7]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 12.3, Avg: 12.6, Max: 12.7, Diff: 0.4, Sum: 101.1]
      [GC Worker End (ms): Min: 669.9, Avg: 669.9, Max: 669.9, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.1 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.2 ms]
      [Humongous Reclaim: 0.2 ms]
      [Free CSet: 0.2 ms]
   [Eden: 216.0M(216.0M)->0.0B(178.0M) Survivors: 18.0M->30.0M Heap: 502.2M(2048.0M)->307.2M(2048.0M)]
 [Times: user=0.00 sys=0.11, real=0.01 secs] 
2020-10-29T02:06:03.149+0800: 0.721: [GC pause (G1 Evacuation Pause) (young), 0.0125511 secs]
   [Parallel Time: 11.2 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 721.2, Avg: 721.8, Max: 723.3, Diff: 2.1]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.5, Diff: 0.5, Sum: 1.8]
         [Processed Buffers: Min: 0, Avg: 2.3, Max: 4, Diff: 4, Sum: 18]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 8.9, Avg: 10.2, Max: 10.7, Diff: 1.8, Sum: 81.4]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 0.7]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 3, Diff: 2, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 9.1, Avg: 10.6, Max: 11.2, Diff: 2.1, Sum: 85.1]
      [GC Worker End (ms): Min: 732.4, Avg: 732.4, Max: 732.4, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.2 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.4 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.2 ms]
   [Eden: 178.0M(178.0M)->0.0B(240.0M) Survivors: 30.0M->26.0M Heap: 532.3M(2048.0M)->357.1M(2048.0M)]
 [Times: user=0.06 sys=0.03, real=0.01 secs] 
2020-10-29T02:06:03.229+0800: 0.801: [GC pause (G1 Evacuation Pause) (young), 0.0143146 secs]
   [Parallel Time: 13.1 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 800.8, Avg: 800.9, Max: 801.1, Diff: 0.3]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.8]
      [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 1.9]
         [Processed Buffers: Min: 0, Avg: 2.9, Max: 5, Diff: 5, Sum: 23]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 12.4, Avg: 12.5, Max: 12.7, Diff: 0.4, Sum: 100.3]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.8]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
      [GC Worker Total (ms): Min: 12.8, Avg: 13.0, Max: 13.1, Diff: 0.3, Sum: 104.3]
      [GC Worker End (ms): Min: 813.9, Avg: 813.9, Max: 814.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.0 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.2 ms]
      [Humongous Reclaim: 0.2 ms]
      [Free CSet: 0.2 ms]
   [Eden: 240.0M(240.0M)->0.0B(304.0M) Survivors: 26.0M->34.0M Heap: 659.9M(2048.0M)->431.6M(2048.0M)]
 [Times: user=0.02 sys=0.00, real=0.02 secs] 
2020-10-29T02:06:03.336+0800: 0.908: [GC pause (G1 Evacuation Pause) (young), 0.0182067 secs]
   [Parallel Time: 17.0 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 907.8, Avg: 907.9, Max: 907.9, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.1, Sum: 1.0]
      [Update RS (ms): Min: 0.2, Avg: 0.3, Max: 0.6, Diff: 0.4, Sum: 2.2]
         [Processed Buffers: Min: 0, Avg: 3.0, Max: 4, Diff: 4, Sum: 24]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.3]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 16.0, Avg: 16.3, Max: 16.4, Diff: 0.4, Sum: 130.5]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
         [Termination Attempts: Min: 1, Avg: 1.8, Max: 3, Diff: 2, Sum: 14]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 16.8, Avg: 16.9, Max: 16.9, Diff: 0.1, Sum: 135.0]
      [GC Worker End (ms): Min: 924.7, Avg: 924.7, Max: 924.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.1 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.2 ms]
      [Humongous Reclaim: 0.3 ms]
      [Free CSet: 0.3 ms]
   [Eden: 304.0M(304.0M)->0.0B(329.0M) Survivors: 34.0M->43.0M Heap: 822.4M(2048.0M)->520.6M(2048.0M)]
 [Times: user=0.08 sys=0.06, real=0.02 secs] 
2020-10-29T02:06:03.437+0800: 1.009: [GC pause (G1 Evacuation Pause) (young), 0.0200811 secs]
   [Parallel Time: 18.7 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 1009.2, Avg: 1009.2, Max: 1009.4, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
      [Update RS (ms): Min: 0.2, Avg: 0.3, Max: 0.8, Diff: 0.6, Sum: 2.5]
         [Processed Buffers: Min: 0, Avg: 3.0, Max: 4, Diff: 4, Sum: 24]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.3]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 17.2, Avg: 17.9, Max: 18.1, Diff: 0.8, Sum: 142.9]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 1.3]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 3, Diff: 2, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 18.3, Avg: 18.5, Max: 18.6, Diff: 0.3, Sum: 148.0]
      [GC Worker End (ms): Min: 1027.7, Avg: 1027.7, Max: 1027.8, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 1.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.2 ms]
      [Humongous Reclaim: 0.4 ms]
      [Free CSet: 0.3 ms]
   [Eden: 329.0M(329.0M)->0.0B(411.0M) Survivors: 43.0M->47.0M Heap: 922.1M(2048.0M)->595.3M(2048.0M)]
 [Times: user=0.05 sys=0.03, real=0.02 secs] 
2020-10-29T02:06:03.627+0800: 1.199: [GC pause (G1 Evacuation Pause) (young), 0.0270224 secs]
   [Parallel Time: 25.5 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 1199.4, Avg: 1199.7, Max: 1200.4, Diff: 1.0]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.9]
      [Update RS (ms): Min: 0.0, Avg: 0.3, Max: 0.4, Diff: 0.4, Sum: 2.3]
         [Processed Buffers: Min: 0, Avg: 3.8, Max: 7, Diff: 7, Sum: 30]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.3]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 24.1, Avg: 24.5, Max: 24.7, Diff: 0.5, Sum: 195.6]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.7]
         [Termination Attempts: Min: 1, Avg: 2.9, Max: 5, Diff: 4, Sum: 23]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 24.4, Avg: 25.1, Max: 25.4, Diff: 1.0, Sum: 201.1]
      [GC Worker End (ms): Min: 1224.8, Avg: 1224.8, Max: 1224.9, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 1.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.2 ms]
      [Humongous Reclaim: 0.3 ms]
      [Free CSet: 0.4 ms]
   [Eden: 411.0M(411.0M)->0.0B(957.0M) Survivors: 47.0M->58.0M Heap: 1109.8M(2048.0M)->687.8M(2048.0M)]
 [Times: user=0.09 sys=0.08, real=0.03 secs] 
Heap
 garbage-first heap   total 2097152K, used 723212K [0x0000000080000000, 0x0000000080104000, 0x0000000100000000)
  region size 1024K, 73 young (74752K), 58 survivors (59392K)
 Metaspace       used 4010K, capacity 4572K, committed 4864K, reserved 1056768K
  class space    used 443K, capacity 460K, committed 512K, reserved 1048576K

```
---

## 周四作业二

使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例

---
压测命令：sb -u http://localhost:8086/api/hello -c 20 -N 60
- java -jar gateway-server-0.0.1-SNAPSHOT.jar

```
Status 200:    230131

RPS: 3758.8 (requests/second)
Max: 305ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 18ms
```

---
- java -jar -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar

```
Status 200:    231978

RPS: 3799.4 (requests/second)
Max: 80ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 20ms
```

---

- java -jar -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar

```
Status 200:    227738

RPS: 3720.8 (requests/second)
Max: 92ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 16ms
```
---
- java -jar -Xms2048m -Xmx2048m gateway-server-0.0.1-SNAPSHOT.jar

```
Status 200:    243971

RPS: 3985.5 (requests/second)
Max: 324ms
Min: 0ms
Avg: 0.4ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 14ms
```

---
- java -jar -XX:+UseConcMarkSweepGC -Xms2048m -Xmx2048m gateway-server-0.0.1-SNAPSHOT.jar

```
Status 200:    260274

RPS: 4254.1 (requests/second)
Max: 205ms
Min: 0ms
Avg: 0.2ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 1ms
  98%   below 3ms
  99%   below 5ms
99.9%   below 14ms
```

---
- java -jar -XX:+UseG1GC -Xms2048m -Xmx2048m gateway-server-0.0.1-SNAPSHOT.jar

```
Status 200:    253483

RPS: 4140.5 (requests/second)
Max: 282ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 2ms
  98%   below 4ms
  99%   below 5ms
99.9%   below 14ms
```
## 总结：
1. jdk8默认UseParallelGC
2. 不指定Xmx为总内存1/4，Xms较小
3. 单次GC时间一般情况是：CMS<G1<UseSerialGC<ParallelGC
4. 专用服务器应该设置Xms=Xmx，配置足够内存。实验中效果明显
5. GC的时间跟堆大小相关
6. 小内存多线程并发下，G1退化

---
## 周六作业二
写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 Github
```java
package pers.ryan.nio;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {
    public static void main(String[] args) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://127.0.0.1:8801").build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }
}

```