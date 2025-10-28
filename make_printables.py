import json
import os
import shutil

from pdf2image import convert_from_path
from PyPDF2 import PdfReader, PdfWriter
from reportlab.lib.pagesizes import A4
from reportlab.pdfgen import canvas

# === CONFIG ===
SLIDES_FOLDER = "./algorithms/part2/slides"
NOTES_JSON_PATH = f"{SLIDES_FOLDER}/notes.json"
OUTPUT_FOLDER = f"{SLIDES_FOLDER}/printable"

# === MAIN ===


def parse_pages(pages_str):
    pages = []
    for part in pages_str.split(","):
        if "-" in part:
            start, end = map(int, part.split("-"))
            pages.extend(range(start, end + 1))
        else:
            pages.append(int(part))
    return [p - 1 for p in pages]  # 0-based for PyPDF2


def create_printable_pdf():
    if not os.path.exists(NOTES_JSON_PATH):
        print(f"❌ notes.json not found at: {NOTES_JSON_PATH}")
        return

    os.makedirs(OUTPUT_FOLDER, exist_ok=True)

    with open(NOTES_JSON_PATH, "r") as f:
        notes_data = json.load(f)

    for pdf_name, page_str in notes_data.items():
        input_pdf_path = os.path.join(SLIDES_FOLDER, pdf_name)
        output_pdf_path = os.path.join(OUTPUT_FOLDER, f"print-{pdf_name}")

        if os.path.exists(output_pdf_path):
            print(f"⚠️  Skipping {pdf_name} — printable version already exists.")
            continue

        if not os.path.exists(input_pdf_path):
            print(f"❌ Missing PDF: {pdf_name}")
            continue

        print(f"🧩 Processing: {pdf_name}")

        # Step 1: Extract selected pages
        reader = PdfReader(input_pdf_path)
        writer = PdfWriter()
        for page_num in parse_pages(page_str):
            if 0 <= page_num < len(reader.pages):
                writer.add_page(reader.pages[page_num])
        selected_pdf_path = os.path.join(OUTPUT_FOLDER, f"selected-{pdf_name}")
        with open(selected_pdf_path, "wb") as out_f:
            writer.write(out_f)

        # Step 2: Convert to images and create 2-per-page A4 PDF
        images = convert_from_path(selected_pdf_path, dpi=150)
        c = canvas.Canvas(output_pdf_path, pagesize=A4)
        width, height = A4
        img_height = height / 2

        for i in range(0, len(images), 2):
            y_positions = [img_height, 0]
            for j in range(2):
                if i + j < len(images):
                    img_path = f"{OUTPUT_FOLDER}/tmp-{i+j}.jpg"
                    images[i + j].save(img_path, "JPEG")
                    c.drawImage(
                        img_path, 0, y_positions[j], width=width, height=img_height
                    )
                    os.remove(img_path)
            c.showPage()

        c.save()
        os.remove(selected_pdf_path)

        print(f"✅ Created printable PDF: {output_pdf_path}")

    # Step 3: Clean up temporary folder
    if os.path.exists(OUTPUT_FOLDER) and not os.listdir(OUTPUT_FOLDER):
        shutil.rmtree(OUTPUT_FOLDER)
        print(f"🧹 Removed temporary folder: {OUTPUT_FOLDER}")
    else:
        print(f"📂 Kept output folder: {OUTPUT_FOLDER}")


if __name__ == "__main__":
    create_printable_pdf()
