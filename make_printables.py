import json
import os
from io import BytesIO

from pdf2image import convert_from_bytes
from PyPDF2 import PdfReader, PdfWriter
from reportlab.lib.pagesizes import A4
from reportlab.lib.units import mm
from reportlab.pdfgen import canvas

SLIDES_FOLDER = "./algorithms/part2/slides"
NOTES_JSON_PATH = f"{SLIDES_FOLDER}/notes.json"


def extract_pages(pdf_path, page_spec):
    reader = PdfReader(pdf_path)
    writer = PdfWriter()
    for part in page_spec.split(","):
        if "-" in part:
            start, end = map(int, part.split("-"))
            for i in range(start, end + 1):
                writer.add_page(reader.pages[i - 1])
        else:
            i = int(part)
            writer.add_page(reader.pages[i - 1])
    out = BytesIO()
    writer.write(out)
    out.seek(0)
    return out


def make_two_per_page(input_pdf, output_pdf):
    """Create an A4 PDF with 2 slides per page (portrait layout)."""
    with open(input_pdf, "rb") as f:
        images = convert_from_bytes(f.read(), dpi=150)

    packet = BytesIO()
    c = canvas.Canvas(packet, pagesize=A4)
    width, height = A4
    margin = 10 * mm
    usable_width = width - 2 * margin
    usable_height = (height - 3 * margin) / 2  # two slides per page

    for i in range(0, len(images), 2):
        for j in range(2):
            if i + j >= len(images):
                continue
            y_offset = height - margin - (j + 1) * usable_height - j * margin
            img = images[i + j]
            c.drawInlineImage(
                img,
                margin,
                y_offset,
                usable_width,
                usable_height,
                preserveAspectRatio=True,
                anchor="n",
            )
        c.showPage()

    c.save()
    packet.seek(0)

    with open(output_pdf, "wb") as f:
        f.write(packet.read())


def main():
    with open(NOTES_JSON_PATH, "r") as f:
        notes = json.load(f)

    os.makedirs(f"{SLIDES_FOLDER}/selected", exist_ok=True)
    os.makedirs(f"{SLIDES_FOLDER}/print_ready", exist_ok=True)

    for filename, pages in notes.items():
        src = f"{SLIDES_FOLDER}/{filename}"
        selected_pdf = (
            f"{SLIDES_FOLDER}/selected/{filename.replace('.pdf', '_selected.pdf')}"
        )
        print_ready_pdf = (
            f"{SLIDES_FOLDER}/print_ready/{filename.replace('.pdf', '_print.pdf')}"
        )

        if not os.path.exists(src):
            print(f"❌ File not found: {src}")
            continue

        print(f"📘 Processing {filename} -> pages {pages}")
        extracted = extract_pages(src, pages)
        with open(selected_pdf, "wb") as f:
            f.write(extracted.read())

        make_two_per_page(selected_pdf, print_ready_pdf)
        print(f"✅ Created {print_ready_pdf}\n")


if __name__ == "__main__":
    main()
